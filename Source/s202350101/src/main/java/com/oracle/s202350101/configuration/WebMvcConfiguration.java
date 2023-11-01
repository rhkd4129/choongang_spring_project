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
		registry.addInterceptor(new SampleInterceptor()).
				addPathPatterns("/main")
		;
	}

}
