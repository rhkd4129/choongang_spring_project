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
		// 누군가가 URL로  /interCeptor라고 친다면 SampleInterceptor() 처리 해줌
		registry.addInterceptor(new SampleInterceptor())
				// interceptor 적용
			//	.addPathPatterns("/mypage_update")
				.addPathPatterns("/**")
				
				// interceptor 적용하지 않겠다
				.excludePathPatterns("/user_login", "/user_join_write", "/user_join_agree", "/user_find_pw"
						, "/user_find_pw_new/**", "/user_find_id", "/user_find_id_result", "/user_find_pw_auth"
						, "/send_save_mail", "/write_user_info", "/id_confirm", "/user_find_pw_update", "/user_login_check"
						, "/bootstrap-5.3.2-examples/assets/dist/css/bootstrap.min.css"
						)
				;
		
		registry.addInterceptor(new adminInterceptor())		//	어드민 권한 조회
				.addPathPatterns("/admin_add_class", "/admin_del_class", "/admin_class_list", "/admin_board",
				"/admin_projectmanager", "/admin_approval");
	}

}
