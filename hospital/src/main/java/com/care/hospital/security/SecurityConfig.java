package com.care.hospital.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.formLogin(login -> login.disable()).
		headers(header -> header.cacheControl(cc -> cc.disable())).
		csrf( csrf -> csrf.disable());
						
		return http.build();
			
	}

  
}

