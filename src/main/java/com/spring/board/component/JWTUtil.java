package com.spring.board.component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtil {

  private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
      System.out.println("call JWTUtil");

    secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
  }

  public String getUsername(String token) {
    System.out.println("call getUsername");

    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
  }

  public List<String> getRole(String token) {

    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", List.class);
  }

  public Boolean isExpired(String token) {

    System.out.println("call jwtUtil.isExpired");
    System.out.println("isExpired token : " + token);
/*    if(token == null || token == ""){
      System.out.println("isExpired if chk");
      throw new JwtException("accessToken not valid");
    }*/
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
  }

  public String getCategory(String token) {

    System.out.println("call jwtUtil getCategory");
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
  }

  public String createJwt(String category, String username, List<String> role, Long expiredMs) {

    return Jwts.builder()
            .claim("category", category)
            .claim("username", username)
            .claim("role", role)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiredMs))
            .signWith(secretKey)
            .compact();
  }
}