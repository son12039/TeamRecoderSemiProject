package com.damoim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	/*
	 * 성철
	 * 일단 조건 전부 허용
	 * 나중에 로그인시만 볼수있는 페이지 처리들 해야함
	 * 로그인, 로그아웃 페이징 이동처리도 여기가 가져감...?
	 * */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(authorize ->
					authorize
						.anyRequest().permitAll()
						)		
				.formLogin(login -> login
						.loginPage("/loginPage")
						.permitAll())
				.logout(logout -> logout
						.logoutSuccessUrl("/")
						.permitAll())
				.build();
	}

}
