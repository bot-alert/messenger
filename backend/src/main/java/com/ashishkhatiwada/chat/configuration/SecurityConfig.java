package com.ashishkhatiwada.chat.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(req -> req.anyRequest().permitAll())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
