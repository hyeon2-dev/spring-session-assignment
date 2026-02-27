package com.example.springsessionassignment.common.filter;

import com.example.springsessionassignment.common.consts.Const;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter{

    private static final String[] WHITE_LIST = {"/", "/signup", "/login", "/logout"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse)  response;
        String requestURI = httpRequest.getRequestURI();

        // 화이트리스트에 포함되지 않는 경우, 로그인 여부 확인
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            // false: 세션이 없으면 새로 생성, true: 세션이 없으면 null 반환
            if (session == null || session.getAttribute(Const.LOGIN_MEMBER) == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인해주세요");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
