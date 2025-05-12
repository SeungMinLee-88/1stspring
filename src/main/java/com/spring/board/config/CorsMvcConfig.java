package com.spring.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry corsRegistry) {

    corsRegistry.addMapping("/**")
            //.exposedHeaders("userName")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용 메서드
            .allowedHeaders("X-Custom-Header", "Content-Type") // 허용 헤더
            .exposedHeaders("X-Custom-Header") // 클라이언트에서 접근 가능한 헤더
            .allowCredentials(true) // 쿠키 포함 여부
            .allowedOrigins("localhost:3000");
  }

}