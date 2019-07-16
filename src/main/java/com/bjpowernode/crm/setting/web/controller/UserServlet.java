package com.bjpowernode.crm.setting.web.controller;

import com.alibaba.fastjson.JSON;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.setting.exception.LoginException;
import com.bjpowernode.crm.setting.service.UserService;
import com.bjpowernode.crm.setting.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import com.bjpowernode.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-13
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String path = request.getServletPath();
        if ("/setting/user/login.do".equals(path)) {
            login(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        //响应包中的json
        Map<String, Object> map = new HashMap<>();

        //免登陆
        String unLogin = request.getParameter("unLogin");
        System.out.println(unLogin);
        //根据请求对象获得对应的实体类
        Map<String, String> u = getMap(request);

        UserService service = (UserService) ServiceFactory.getService(UserServiceImpl.class);

        try {
            User u1 = service.login(u);
            request.getSession().setAttribute("user", u1);
            map.put("success", true);
            if (unLogin != null) {
                Cookie cookie = new Cookie("loginAct", u1.getLoginAct());
                cookie.setMaxAge(999);
                response.addCookie(cookie);
            }
            response.getWriter().print(JSON.toJSONString(map));
        } catch (LoginException e) {
            map.put("success", false);
            map.put("msg", e.getMessage());
            try {
                response.getWriter().print(JSON.toJSONString(map));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> getMap(HttpServletRequest request) {
        String loginAct = request.getParameter("loginAct");
        String loginPwd = MD5Util.getMD5(request.getParameter("loginPwd"));
        String remoteIp = request.getRemoteHost();
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        map.put("remoteIp", remoteIp);
        return map;
    }
}
