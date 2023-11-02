package com.oracle.s202350101.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.s202350101.service.mkhser.SampleInterceptor;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 누군가가 URL로  /interCeptor라고 친다면 SampleInterceptor() 처리 해줌
		registry.addInterceptor(new SampleInterceptor())
//				.addPathPatterns("/user_login_check")
				//.addPathPatterns("/mypost_board_list")
				.addPathPatterns("/mypage_update")
//				.addPathPatterns("/mypage_main")
				.addPathPatterns("/mypage_main")
//				.addPathPatterns("/**")
				
				
//				.addPathPatterns("/main/*")
				.excludePathPatterns("/user_login")
//				.addPathPatterns("/mypost_board_list/**")
				
				
				// 입력 패턴은 interceptor 적용
				// 입력 패턴은 interceptor 적용하지 않겠다
		;
	}

}
