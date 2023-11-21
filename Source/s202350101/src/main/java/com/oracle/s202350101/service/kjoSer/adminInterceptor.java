package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
public class adminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();        //  요청받은 페이지

        log.info("어드민 사용자 인터셉터");
        log.info("requestURI : {}", requestURI);            //  요청받은 페이지

        UserInfo ui = (UserInfo) request.getSession(false).getAttribute("userInfo");        //  세션 내 UserInfo 받아옴
        String usID = ui.getUser_id();            //  사용자 UserInfo  ID

        log.info("admin {}: ",usID.equals("admin"));
        if (!usID.equals("admin")) {
            log.info("일반 사용자 요청");

            //  헤더의 내용 중 요청 이전의 URL을 String으로 받음.
            String referer = request.getHeader("Referer");
            String alertMessage = "관리자 권한이 없습니다.";

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + alertMessage + "'); window.location.href='" + referer + "';</script>");
            out.flush();
            out.close();

            return false;
        }
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
