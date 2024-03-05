package org.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity ) throws Exception {
    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    httpSecurity.sessionManagement( (sessionManager) -> sessionManager.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS));

    httpSecurity.authorizeHttpRequests( ( authorizeHttpRequest ) ->
            authorizeHttpRequest
                    .requestMatchers( "/api/users/signup" ).permitAll()
                    .requestMatchers( "/api/users/login" ).permitAll()
                    .anyRequest().authenticated()
    );

    return httpSecurity.build();
  }
}
