package com.oracle.s202350101.service.mkhser;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;

public class SampleInterceptor implements HandlerInterceptor {
	
	public SampleInterceptor() {
		
	}
	
	// 3번째 실행
	@Override
	public void postHandle(HttpServletRequest request, 
						   HttpServletResponse response, 
						   Object handler,
						   ModelAndView modelAndView) throws Exception {

//			System.out.println("post handle....................................");

	}
	
	// 1번째 실행
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler
							 ) throws Exception {
//		System.out.println("pre handle....................................");
		// 세션이 있으면 만들지마 (로그인할때 이미 만듬)
		System.out.println();
		HttpSession session = request.getSession(false);
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		
		if(userInfo == null) {
			System.out.println("preHandle userInfo is no exists");
			response.sendRedirect("user_login");
			return false;	// 컨트롤러 진행 x
		} else
		return true;	// 컨트롤러 진행 o
	}

}

		
