package com.oracle.s202350101.service.mkhser;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.oracle.s202350101.model.UserInfo;

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
		UserInfo userInfo = (UserInfo) modelAndView.getModel().get("userInfo");
		System.out.println("USERINFO : "+userInfo);
		if(userInfo.getUser_id() == null) {
			System.out.println("userInfo Not exists");
//			request.setAttribute("userInfo",userInfo);
//			request.setAttribute("userInfo",userInfo);
//			return;
			response.sendRedirect("user_login");

		} else {
			System.out.println("userInfo exists");
			request.getSession().setAttribute("userInfo", userInfo);
			System.out.println("UserInfo: "+request.getAttribute("userInfo"));
			request.setAttribute("userInfo",userInfo);
		//	response.sendRedirect(request.getContextPath()+"/main");
			response.sendRedirect("/main");
			return;
		}

	}
	
	// 1번째 실행
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler) throws Exception {
		
		System.out.println("pre handle....................................");
		// 로그인 세션이 있는지 없는지 먼저 검증?

//		HandlerMethod method = (HandlerMethod) handler;
//		Method methodObj = method.getMethod();
//		System.out.println("Bean: " + method.getBean());
//		System.out.println("Method: " + methodObj);
		
		return true;
	}

}
