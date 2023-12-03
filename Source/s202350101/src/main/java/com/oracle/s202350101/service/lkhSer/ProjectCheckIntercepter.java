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
@Slf4j
public class ProjectCheckIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("프로젝트 체크 인터셉터 시작");
        String requestURI = request.getRequestURI();        //  요청받은 페이지

        log.info("requestURI : {}", requestURI);            //  요청받은 페이지

        UserInfo ui = (UserInfo) request.getSession(false).getAttribute("userInfo");        //  세션 내 UserInfo 받아옴

        if (ui.getProject_id() == 0) {

            log.info("일반 사용자 요청");

            //  헤더의 내용 중 요청 이전의 URL을 String으로 받음.
            String referer = request.getHeader("Referer");
            String alertMessage = "프로젝트 정보가 없습니다.";

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertMessage + "'); window.location.href='" + referer + "';</script>");
            out.flush();
            out.close();

            return false;
        }
        log.info("프로젝트 체크완료 성공");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}