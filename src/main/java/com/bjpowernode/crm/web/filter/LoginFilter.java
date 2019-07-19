package com.bjpowernode.crm.web.filter;

import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            String path = request.getServletPath();
            if ("login.jsp".equals(path) || "/setting/user/login.do".equals(path)) {
                chain.doFilter(req, resp);
            } else {
                Cookie[] cookies = request.getCookies();
                if (cookies != null && cookies.length != 0) {
                    for (Cookie cookie : cookies) {
                        if ("loginAct".equals(cookie.getName())) {
                            chain.doFilter(req, resp);
                            return;
                        }
                    }
                }
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                chain.doFilter(req, resp);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }
}
