package com.oracle.s202350101.service.lkhSer;

import com.oracle.s202350101.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

//@Nullable
public class ProjectCheckIntercepter implements HandlerInterceptor {
    public ProjectCheckIntercepter(){};
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println("project_check_intercepter");
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        if(userInfo.getProject_id()== null){
            // 리다이렉트
            response.sendRedirect("main");
            // 처리를 중단하기 위해 false 반환
            return false;
        }
        System.out.println("프로젝트 정보 있다 통과,");
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                             ModelAndView modelAndView) throws Exception {
    }

}
