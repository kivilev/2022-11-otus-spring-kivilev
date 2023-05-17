
/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable().cors().and()
                .httpBasic()
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/",
                                "/favicon.ico",
                                "/**/*.png",
                                "/**/*.gif",
                                "/**/*.svg",
                                "/**/*.jpg",
                                "/**/*.html",
                                "/**/*.css",
                                "/**/*.js").permitAll()
                        .antMatchers("/authors").authenticated()
                        .antMatchers("/comments").authenticated()
                        .antMatchers("/books").authenticated()
                        .antMatchers("/genres").authenticated()
                        .antMatchers("/books/add").authenticated()
                        .antMatchers("/books/edit").authenticated()
                        .antMatchers("/api/v1/**").authenticated()
                        .anyRequest().denyAll()
                )
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
