package com.oracle.s202350101.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 로그인 보안 모듈 안쓰겠다 설정
//		http.csrf().disable()        //	요청을 위조하여 사용자의 권한을 이용해 서버에 대한 악성공격을 하는 것
//				.cors().disable()        //	다른 출처와 리소스를 공유하는 것
//				.authorizeRequests(request -> request
//						.anyRequest().authenticated())                        //	출처 : Protocol + Host + Port번호
//				.formLogin(login -> login
//						.loginPage("/user_login")				//	로그인 페이지
//						.loginProcessingUrl("/user_login_check")		//	로그인 action페이지
//						.defaultSuccessUrl("/main", true)	//	로그인 성공시
//						.permitAll())
//				.logout(Customizer.withDefaults())
//				.sessionManagement()
//				.maximumSessions(1)
//				.maxSessionsPreventsLogin(false);
//				//maxSessionPreventsLogin : true 일 경우 기존에 동일한 사용자가 로그인한 경우에는 login 이 안된다.
//				// false 일경우는 로그인이 되고 기존 접속된 사용자는 Session이 종료된다. false 가 기본이다.



		http.csrf().disable();
		http.authorizeRequests()
				.anyRequest()
				.permitAll();


		
		return http.build();
	}


}
