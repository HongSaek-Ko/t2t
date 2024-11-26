package org.t2t.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class IncludingUrlFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter - init");
    }

    @Override
    public void destroy() {
        log.info("LogFilter - destroy");
    }

    // 중추 개념의 메서드
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 우리가 아는 request, response 객체 타입으로 형변환하여 사용
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession  session = request.getSession();
        session.setAttribute("http_session_current_url", request.getRequestURI());
        log.info("doFilter - requestURI: {}", request.getRequestURI());
        filterChain.doFilter(request, response); // 필수 작성 - 다음 필터가 있으면 필터 호출, 없다면 서블릿 호출
    }
}
