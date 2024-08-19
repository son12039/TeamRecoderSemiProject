package com.damoim.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
				.csrf(csrf->csrf.disable())    // post일때 csrf를 차단시켜야 가능
				.formLogin(login ->
				login
					.loginPage("/loginPage")      // 로그인할 때 GET 페이지 -> 로그인 페이지
					.loginProcessingUrl("/login") // 로그인할 때 POST 요청 -> 로그인 요청!
					.defaultSuccessUrl("/",true) // 로그인 성공했을때
					.failureUrl("/loginFail")
					
				//	.defaultSuccessUrl("/loginPage", false)
				//	.failureUrl("/loginPage")
				//	.failureHandler(new DomainFailureHandler()) // 로그인 실패했을때 에러 처리
				.permitAll()// 로그인 성공 했을때 /로 가겠다 
						)  
				.logout(logout->
				logout.logoutUrl("/logout")   // 로그아웃 요청 URL 
				.logoutSuccessUrl("/")         // 로그아웃 성공했을때 
				.permitAll()
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // 세션방식으로하겠다 
				.authorizeHttpRequests(authorize ->
				 authorize		                                          
				 	//   .requestMatchers("/like","/unlike").authenticated()										     				 											// 권한이 ROLE_ADMIN인 경우만 접근이 가능
				       .anyRequest().permitAll()                
				  )	
				
				.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}
