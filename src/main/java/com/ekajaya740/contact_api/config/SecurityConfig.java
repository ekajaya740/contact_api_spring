package com.ekajaya740.contact_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable() // 👈 This disables CSRF checks
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated())
        .httpBasic(); // Use HTTP Basic Authentication

    return http.build();
  }
}
