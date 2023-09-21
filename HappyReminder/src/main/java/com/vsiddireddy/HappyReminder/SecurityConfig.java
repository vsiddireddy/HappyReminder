package com.vsiddireddy.HappyReminder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    try {
						auth.requestMatchers("/").permitAll().and().csrf().disable();
					} catch (Exception e) {
						e.printStackTrace();
					}
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(loginConfigurer -> loginConfigurer.successHandler(new AuthDefaultHandler())
                .defaultSuccessUrl("/user"))
                .build();
	}
}