package com.bjpowernode.crm.workbench.web.controller;

import com.alibaba.fastjson.JSON;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.setting.service.UserService;
import com.bjpowernode.crm.setting.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.BeanUtil;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public class ActivityServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/workbench/activity/allOwner.do".equals(servletPath)) {
            //查询市场活动的所有者
            allOwner(request, response);
        } else if ("/workbench/activity/add.do".equals(servletPath)) {
            //添加市场活动
            addActivity(request, response);
        }
    }

    //添加市场活动
    private void addActivity(HttpServletRequest request, HttpServletResponse response) {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        Activity activity = BeanUtil.newBean(Activity.class, request);
        System.out.println(activity);
        activity.setId(UUIDUtil.getUUID());
        activity.setCreateBy(((User) (request.getSession().getAttribute("user"))).getName());
        service.add(activity);
    }

    //查询市场活动的所有者
    private void allOwner(HttpServletRequest request, HttpServletResponse response) {
        UserService service = (UserService) ServiceFactory.getService(UserServiceImpl.class);
        List<User> uList = service.findAll();
        try {
            response.getWriter().print(JSON.toJSONString(uList));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
