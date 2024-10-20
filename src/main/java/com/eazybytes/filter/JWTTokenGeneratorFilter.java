package com.eazybytes.filter;

import com.eazybytes.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
    //필터가 한번만 실행되어야 함

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

        if(null != authentication) {
            Environment env = getEnvironment();
            if (null != env) {
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                        ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); //바이트열 호출
                String jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token") // issuer : 어디서 제공했는지
                        .claim("username", authentication.getName()) //JWT에 사용자 정보 넣기
                        .claim("authorities", authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(","))) // 인증 객체 내에 있는 권한 기록을 반복
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + 30000000))
                        .signWith(secretKey).compact(); //디지털 서명 생성
                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
            }
        }
        filterChain.doFilter(request, response);
    }

    //값이 false일때 필터가 호출되지 않음
    //"/user" 일때만 필터 호출하기 위해서
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }
}
