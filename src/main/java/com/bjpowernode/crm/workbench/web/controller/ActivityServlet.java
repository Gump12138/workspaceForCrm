package com.bjpowernode.crm.workbench.web.controller;

import com.alibaba.fastjson.JSON;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.setting.service.UserService;
import com.bjpowernode.crm.setting.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.BeanUtil;
import com.bjpowernode.crm.utils.ServiceFactory;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public class ActivityServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        if ("/workbench/activity/allOwner.do".equals(servletPath)) {

            //所有者
            allOwner(request, response);

        } else if ("/workbench/activity/add.do".equals(servletPath)) {

            //添加市场活动
            addActivity(request, response);

        } else if ("/workbench/activity/search.do".equals(servletPath)) {

            //动态查询市场活动
            searchActivity(request, response);

        } else if ("/workbench/activity/delete.do".equals(servletPath)) {

            //删除市场活动及关联
            deleteActivity(request, response);

        } else if ("/workbench/activity/update.do".equals(servletPath)) {

            //更新市场活动
            updateActivity(request, response);

        } else if ("/workbench/activity/selectOne.do".equals(servletPath)) {

            //查询用户列表和单个市场活动记录
            selectOne(request, response);

        } else if ("/workbench/activity/detail.do".equals(servletPath)) {

            //市场活动的详细信息
            detail(request, response);

        } else if ("/workbench/activity/getRemarkListByAid.do".equals(servletPath)) {

            //查询市场活动备注
            getRemarkListByAid(request, response);

        } else if ("/workbench/activity/saveRemark.do".equals(servletPath)) {

            //保存市场活动备注
            saveRemark(request, response);

        } else if ("/workbench/activity/editRemark.do".equals(servletPath)) {

            //修改市场活动备注
            editRemark(request, response);

        } else if ("/workbench/activity/removeRemark.do".equals(servletPath)) {

            //修改市场活动备注
            removeRemark(request, response);

        }
    }

    private void removeRemark(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        String id = request.getParameter("id");
        boolean flag =service.removeRemark(id);
        response.getWriter().print(JSON.toJSONString(flag));
    }

    private void editRemark(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        ActivityRemark remark = BeanUtil.newBean(ActivityRemark.class, request);
        remark.setEditFlag("1");

        boolean flag = service.updateRemark(remark);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        response.getWriter().print(JSON.toJSONString(map));
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        ActivityRemark remark = BeanUtil.newBean(ActivityRemark.class, request);
        remark.setId(UUIDUtil.getUUID());
        remark.setEditFlag("0");
        boolean flag = service.saveRemark(remark);
        Map<String, Object> map = new HashMap<>();
        if (flag) {
            map.put("success", true);
            map.put("arm", remark);
        } else {
            map.put("success", false);
        }
        response.getWriter().print(JSON.toJSONString(map));
    }

    private void getRemarkListByAid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        String id = request.getParameter("id");
        List<ActivityRemark> activityRemark = service.getRemarkListByAid(id);
        Map<String, Object> map = new HashMap<>();
        if (activityRemark != null && activityRemark.size() != 0) {
            map.put("success", true);
            map.put("arm", activityRemark);
        } else {
            map.put("success", false);
        }
        response.getWriter().print(JSON.toJSONString(map));
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        String id = request.getParameter("id");
        Activity activity = service.detail(id);
        request.setAttribute("activity", activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request, response);
    }

    private void selectOne(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        String id = request.getParameter("id");
        Map<String, Object> map = service.selectOne(id);
        response.getWriter().print(JSON.toJSONString(map));
    }

    //
    private void updateActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        Activity activity = BeanUtil.newBean(Activity.class, request);
        String user = getUser(request);
        activity.setEditBy(user);
        System.out.println(activity);
        boolean flag = service.update(activity);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        response.getWriter().print(JSON.toJSONString(map));
    }

    //
    private void deleteActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        String[] ids = request.getParameterValues("id");
        boolean flag = service.del(ids);
        Map<String, Object> map = new HashMap<>();
        map.put("success", flag);
        response.getWriter().print(JSON.toJSONString(map));
    }

    //
    private void searchActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        Map<String, Object> map = getMap(request);
        PageVo<Activity> vo = service.search(map);
        response.getWriter().print(JSON.toJSONString(vo));
    }

    private Map<String, Object> getMap(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", request.getParameter("name"));
        map.put("owner", request.getParameter("owner"));
        map.put("startDate", request.getParameter("startDate"));
        map.put("endDate", request.getParameter("endDate"));
        Integer pageNo = Integer.valueOf(request.getParameter("pageNo"));
        Integer pageSize = Integer.valueOf(request.getParameter("pageSize"));
        map.put("skipCount", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);

        return map;
    }

    //
    private void addActivity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        Activity activity = BeanUtil.newBean(Activity.class, request);

        activity.setId(UUIDUtil.getUUID());
        String user = getUser(request);
        activity.setCreateBy(user);
        boolean add = service.add(activity);
        Map<String, Object> map = new HashMap<>();
        map.put("success", add);
        response.getWriter().print(JSON.toJSONString(map));
    }

    private String getUser(HttpServletRequest request) {
        String user = "";
        Object u1 = request.getSession().getAttribute("user");
        if (u1 != null) {
            user = ((User) u1).getName();
        }
        if ("".equals(user) || user == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("loginAct".equals(cookie.getName())) {
                    user = cookie.getValue();
                }
            }
        }
        return user;
    }

    //
    private void allOwner(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService service = (UserService) ServiceFactory.getService(UserServiceImpl.class);
        List<User> uList = service.findAll();
        response.getWriter().print(JSON.toJSONString(uList));
    }
}
