package com.eazybytes.filter;

//처음 UI가 서버로 요청을 보내면 CSRF 토큰을 생성 후 보내야 한다.
//UI 애플리케이션으로 보내게 될 모든 응답들에 filter 클래스를 생성해야 한다..?

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if(null != csrfToken.getHeaderName()){
            response.setHeader(csrfToken.getHeaderName(), csrfToken.getToken());
        }
        // 위와같이 헤더에 토큰값만 넣으면 자동으로 스프링 시큐리티가 쿠키를 만들어서 전달한다.

        // JSESSIONID를 로그에 출력
        HttpSession session = request.getSession(false);  // 기존 세션이 없으면 null을 반환
        if (session != null) {
            String sessionId = session.getId();
            logger.info("JSESSIONID: " + sessionId);  // 로그에 JSESSIONID 출력
        }


        filterChain.doFilter(request, response); //nextFilter에 전달됨
    }

}
