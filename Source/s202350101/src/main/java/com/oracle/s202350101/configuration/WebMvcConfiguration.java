package com.oracle.s202350101.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.oracle.s202350101.service.kjoSer.adminInterceptor;
import com.oracle.s202350101.service.mkhser.SampleInterceptor;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		
		registry.addInterceptor(new SampleInterceptor()) //로그인 세션 권한
				// Interceptor 적용
				.addPathPatterns("/**")
				
				// Interceptor 적용하지 않음
				.excludePathPatterns("/user_login", "/user_join_write", "/user_join_agree", "/user_find_pw"
						, "/user_find_pw_new/**", "/user_find_id", "/user_find_id_result", "/user_find_pw_auth"
						, "/send_save_mail", "/write_user_info", "/id_confirm", "/user_find_pw_update", "/user_login_check"
						, "/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css"
						, "/bootstrap-5.3.2-examples/css/sign-in.css"
						, "/bootstrap-5.3.2-dist/css/bootstrap.css"
						)
				;
		
		registry.addInterceptor(new adminInterceptor())		//	어드민 권한 조회
				.addPathPatterns("/admin_add_class", "/admin_del_class", "/admin_class_list", "/admin_board",
				"/admin_projectmanager", "/admin_approval");
	}

}
