package com.oracle.s202350101.configuration;//package com.oracle.s202350101.configuration;
//
//import javax.servlet.DispatcherType;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig_prac {
//	@Bean
//	public BCryptPasswordEncoder encodePwd() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf().disable();
//		http.authorizeRequests()
//				.antMatchers("/user_login", "/user_join_write").permitAll() // 허용 페이지
//				.anyRequest().authenticated(); // 어떠한 요청이라도 인증필요 (다른 페이지는 인증된 사용자만 허용)
//                
//		// 로그인 설정
//		http
//			.formLogin()  // form 방식 로그인 사용
//					.loginPage("/user_login")		// 커스텀 로그인 페이지
//	                .defaultSuccessUrl("/main", true) // login에 성공하면 /main 로 이동
//	                .usernameParameter("user_id")	// login에 필요한 id 값을 email로 설정 (default는 username)
//	                .passwordParameter("user_pw")	// login에 필요한 password 값을 password(default)로 설정
//	                .defaultSuccessUrl("/main");	// login에 성공하면 /main
//		
//		// 로그아웃 설정
//        http
//            .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/main");	// logout에 성공하면 /main
//
//       
//		return http.build();
//	}
//
//}
