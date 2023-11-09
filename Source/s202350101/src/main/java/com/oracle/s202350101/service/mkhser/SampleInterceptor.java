package com.oracle.s202350101.service.mkhser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SampleInterceptor implements HandlerInterceptor {
	
	public SampleInterceptor() {
		
	}
	
	// 3번째 실행
	@Override
	public void postHandle(HttpServletRequest request, 
						   HttpServletResponse response, 
						   Object handler,
						   ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("post handle....................................");
		//UserInfo userInfo = (UserInfo) modelAndView.getModel().get("userInfo");
		System.out.println("postHandle session.userInfo->"+request.getSession().getAttribute("userInfo"));

		String urlGo = (String) modelAndView.getModel().get("urlGo");
		System.out.println("postHandle urlGo->"+urlGo);
		// 기존 로그인 세션이 있는 상태면 urlGo로 이동
		if(request.getSession().getAttribute("userInfo") != null) {
			response.sendRedirect(urlGo);
		} else {
			System.out.println("userInfo Not exists");
			response.sendRedirect("user_login");
		}
	}
	
	// 1번째 실행
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler) throws Exception {
		
		System.out.println("pre handle....................................");
		
		return true;

		
	}

}
