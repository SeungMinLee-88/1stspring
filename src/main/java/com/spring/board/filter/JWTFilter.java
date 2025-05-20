package com.spring.board.filter;

import com.spring.board.component.JWTUtil;
import com.spring.board.dto.RoleDTO;
import com.spring.board.entity.RoleEntity;
import com.spring.board.entity.RoleUserEntity;
import com.spring.board.entity.UserEntity;
import com.spring.board.repository.RoleRepository;
import com.spring.board.repository.RoleUserRepository;
import com.spring.board.repository.UserRepository;
import com.spring.board.service.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JWTFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;



  public JWTFilter(JWTUtil jwtUtil) {

    this.jwtUtil = jwtUtil;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    System.out.println("call doFilterInternal");
    // 헤더에서 access키에 담긴 토큰을 꺼냄

    String accessToken = request.getHeader("access");

    System.out.println("accessToken : " + accessToken);

    System.out.println("getRequestURL : " + request.getRequestURI());
// 토큰이 없다면 다음 필터로 넘김
    if (accessToken == null) {
      System.out.println("accessToken == null");

      filterChain.doFilter(request, response);

      return;
    }

// 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
    try {
      System.out.println("call JWTFilter.isExpired");
      System.out.println("jwtUtil.isExpired(accessToken) : " + jwtUtil.isExpired(accessToken));
      jwtUtil.isExpired(accessToken);

      System.out.println("call JWTFilter.isExpired end");
    } catch (ExpiredJwtException e) {

      System.out.println("e : " + e);
      System.out.println("call JWTFilter.isExpired catch");
      //response body
      PrintWriter writer = response.getWriter();
      writer.print("accessToken expired");

      //response status code
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //response.addHeader("tokenstatus", "expired");
      return;
    }catch (JwtException e) {

      System.out.println("e : " + e);
      System.out.println("call JWTFilter.isExpired catch");
      //response body
      PrintWriter writer = response.getWriter();
      writer.print("accessToken not valid");

      //response status code
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      //response.addHeader("tokenstatus", "expired");
      return;
    }

    System.out.println("catch end");
// 토큰이 access인지 확인 (발급시 페이로드에 명시)
    String category = jwtUtil.getCategory(accessToken);

    System.out.println("call JWTFilter.getCategory");
    if (!category.equals("access")) {

      //response body
      PrintWriter writer = response.getWriter();
      writer.print("invalid access token");

      //response status code
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    System.out.println("getCategory end");

// username, role 값을 획득
    String userName = jwtUtil.getUsername(accessToken);
    String userRole = jwtUtil.getRole(accessToken);

    System.out.println("JWTFilter userName : " + userName);
    System.out.println("JWTFilter userRole : " + userRole);
    System.out.println("JWTFilter getUsername end");

    UserEntity userEntity = new UserEntity();
    userEntity.setUserName(userName);
    List<RoleEntity> roleEntity = new ArrayList<>();

    List<String> roles = new ArrayList<>();


    CustomUserDetails customUserDetails = new CustomUserDetails(userEntity, roles);

    System.out.println("customUserDetails.getAuthorities() : " + Arrays.toString(customUserDetails.getAuthorities().toArray()));
    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authToken);
    System.out.println("JWTFilter authToken : " + authToken);
    filterChain.doFilter(request, response);
    System.out.println("doFilter end");
  }
}
    /*//request에서 Authorization 헤더를 찾음
    String authorization= request.getHeader("Authorization");

    //Authorization 헤더 검증
    if (authorization == null || !authorization.startsWith("Bearer ")) {

      System.out.println("token null");
      filterChain.doFilter(request, response);

      //조건이 해당되면 메소드 종료 (필수)
      return;
    }

    System.out.println("authorization now");
    //Bearer 부분 제거 후 순수 토큰만 획득
    String token = authorization.split(" ")[1];

    //토큰 소멸 시간 검증
    if (jwtUtil.isExpired(token)) {

      System.out.println("token expired");
      filterChain.doFilter(request, response);

      //조건이 해당되면 메소드 종료 (필수)
      return;
    }

    //토큰에서 username과 role 획득
    String username = jwtUtil.getUsername(token);
    String role = jwtUtil.getRole(token);

    //userEntity를 생성하여 값 set
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(username);
    userEntity.setPassword("temppassword");
    userEntity.setRole(role);

    //UserDetails에 회원 정보 객체 담기
    CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

    //스프링 시큐리티 인증 토큰 생성
    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    //세션에 사용자 등록
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
    System.out.println("authorizaed authorizaed authorizaed");
  }*/
