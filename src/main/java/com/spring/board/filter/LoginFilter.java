package com.spring.board.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.component.JWTUtil;
import com.spring.board.dto.JoinDTO;
import com.spring.board.dto.RoleDTO;
import com.spring.board.dto.RoleUserDTO;
import com.spring.board.dto.UserDto;
import com.spring.board.entity.RefreshEntity;
import com.spring.board.entity.RoleEntity;
import com.spring.board.entity.RoleUserEntity;
import com.spring.board.entity.UserEntity;
import com.spring.board.repository.RefreshRepository;
import com.spring.board.repository.RoleRepository;
import com.spring.board.repository.RoleUserRepository;
import com.spring.board.repository.UserRepository;
import com.spring.board.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {


  private ObjectMapper objectMapper =  new ObjectMapper();


  private final AuthenticationManager authenticationManager;
  private final RefreshRepository refreshRepository;
  private final JWTUtil jwtUtil;
  public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository) {

    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.refreshRepository = refreshRepository;
      setFilterProcessesUrl("/api/v1/user/login");

  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    System.out.println("call attemptAuthentication");
    ServletInputStream inputStream = null;
    try {
      inputStream = request.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String messageBody = null;
    try {
      messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
      System.out.println("messageBody = " + messageBody);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }



    System.out.println("request loginId : " + request.getParameter("loginId"));
    System.out.println("request userPassword : " + request.getParameter("userPassword"));
    //클라이언트 요청에서 username, password 추출
    /*String username = obtainUsername(request);
    String password = obtainPassword(request);*/
    UserDto userDto = new UserDto();
    String loginId = "";
    String userPassword = "";
    try {
      userDto = objectMapper.readValue(messageBody, UserDto.class);
      loginId = userDto.getLoginId();
      userPassword = userDto.getUserPassword();
      ModelMapper mapper = new ModelMapper();


      System.out.println("loginId : " + loginId);
      System.out.println("userDto.getLoginId() : " + userDto.getLoginId());
      System.out.println("userPassword : " + userPassword);
      System.out.println("userDto : " + userDto);

    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    System.out.println("loginId : " + loginId);

    //https://stackoverflow.com/questions/14712685/spring-security-set-grantedauthorities
    Collection<SimpleGrantedAuthority> oldAuthorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ANOTHER");


    List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
    updatedAuthorities.add(authority);
    updatedAuthorities.addAll(oldAuthorities);

    //스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, userPassword, null);

    //token에 담은 검증을 위한 AuthenticationManager로 전달
    System.out.println("authToken : " + authToken);
    return authenticationManager.authenticate(authToken);
  }

  //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
 /* @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    System.out.println("successfulAuthentication success");

    //UserDetailsS
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

    String username = customUserDetails.getUsername();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();

    String role = auth.getAuthority();

    String token = jwtUtil.createJwt(username, role, 600*600*10L);

    response.addHeader("Authorization", "Bearer " + token);
  }*/

  //로그인 실패시 실행하는 메소드
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    response.setStatus(401);
  }

  protected void successfulAuthentication (HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

    //유저 정보
    String userName = authentication.getName();
    //String userId = (String) authentication.getPrincipal();
/*    String userId = (String) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();*/
    System.out.println("userName : " + userName);

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    System.out.println("successfulAuthentication role : " + role);

    //토큰 생성
    /*String access = jwtUtil.createJwt("access", username, role, 20000L);*/
    /*String access = jwtUtil.createJwt("access", username, role, 600000L);*/
    /*
    100000L 20000L 600000L 86400000L
    */
    String access = jwtUtil.createJwt("access", userName, role, 6000000L);
    String refresh = jwtUtil.createJwt("refresh", userName, role, 600000L);

    //Refresh 토큰 저장
    addRefreshEntity(userName, refresh, 20000L);

    //응답 설정
    response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, userName, Response-Header, access" );
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS" );
    response.setHeader("Access-Control-Allow-Origin", "localhost:3000" );
    response.setHeader("Access-Control-Expose-Headers", "userName, access" );
    response.setHeader("access", access );
    response.setHeader("userName", userName );

    response.addCookie(createCookie("refresh", refresh));
    response.setStatus(HttpStatus.OK.value());
  }

  private void addRefreshEntity(String username, String refresh, Long expiredMs) {

    Date date = new Date(System.currentTimeMillis() + expiredMs);
    System.out.println("addRefreshEntity expiredMs : " + expiredMs);

    RefreshEntity refreshEntity = new RefreshEntity();
    refreshEntity.setUsername(username);
    refreshEntity.setRefresh(refresh);
    refreshEntity.setExpiration(date.toString());
    System.out.println("addRefreshEntity date : " + date);

    refreshRepository.save(refreshEntity);
  }

  private Cookie createCookie(String key, String value) {

    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(24*60*60);
    //cookie.setSecure(true);
    //cookie.setPath("/");
    cookie.setHttpOnly(true);

    return cookie;
  }
}