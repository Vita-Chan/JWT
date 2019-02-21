package com.vita.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JwtApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(JwtApplication.class, args);
  }

}
