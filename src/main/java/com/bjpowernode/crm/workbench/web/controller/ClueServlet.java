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
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import com.bjpowernode.crm.workbench.service.impl.ActivityServiceImpl;
import com.bjpowernode.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static jdk.nashorn.api.scripting.ScriptUtils.convert;

/**
 * Author: 甘明波
 * 2019-07-18
 */
public class ClueServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        String path = request.getServletPath();
        if ("/workbench/clue/pageList.do".equals(path)) {
            pageList(request, response);
        } else if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/clue/getUserListAndActivity.do".equals(path)) {
            getUserListAndActivity(request, response);
        } else if ("/workbench/clue/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/clue/getRemarkListByCid.do".equals(path)) {

            getRemarkListByCid(request, response);

        } else if ("/workbench/clue/saveRemark.do".equals(path)) {
            saveRemark(request, response);
        } else if ("/workbench/clue/editRemark.do".equals(path)) {

            editRemark(request, response);

        } else if ("/workbench/clue/removeRemark.do".equals(path)) {

            removeRemark(request, response);

        } else if ("/workbench/clue/getActivityListByCid.do".equals(path)) {

            getActivityListByCid(request, response);

        } else if ("/workbench/clue/unbound.do".equals(path)) {

            unbound(request, response);

        } else if ("/workbench/clue/getActivityListByNameAndNotByClueId.do".equals(path)) {

            getActivityListByNameAndNotByClueId(request, response);

        } else if ("/workbench/clue/bound.do".equals(path)) {

            bound(request, response);

        } else if ("/workbench/clue/convert.do".equals(path)) {

            clueConvert(request, response);

        }
    }

    private void getUserListAndActivity(HttpServletRequest request, HttpServletResponse response) {
    }

    private void clueConvert(HttpServletRequest request, HttpServletResponse response) {
    }

    private void bound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClueService service = (ClueService) ServiceFactory.getService(ClueServiceImpl.class);
        ClueActivityRelation relation = BeanUtil.newBean(ClueActivityRelation.class, request);
        relation.setId(UUIDUtil.getUUID());
        boolean flag = service.bound(relation);
        response.getWriter().print(JSON.toJSONString(flag));
    }

    private void getActivityListByNameAndNotByClueId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ActivityService service = (ActivityService) ServiceFactory.getService(ActivityServiceImpl.class);
        String name = request.getParameter("name");
        String clueId = request.getParameter("clueId");
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("clueId", clueId);
        List<Activity> aList = service.getActivityListByNameAndNotByClueId(map);

        Map<String, Object> objectMap = new HashMap<>();
        if (aList != null && aList.size() != 0) {
            objectMap.put("success", true);
            objectMap.put("aList", aList);
        } else {
            objectMap.put("success", false);
        }
        response.getWriter().print(JSON.toJSONString(objectMap));
    }


    private void unbound(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClueService service = (ClueService) ServiceFactory.getService(ClueServiceImpl.class);
        ClueActivityRelation relation = BeanUtil.newBean(ClueActivityRelation.class, request);
        relation.setId(UUIDUtil.getUUID());
        boolean flag = service.unbound(relation);
        response.getWriter().print(JSON.toJSONString(flag));
    }

    private void getActivityListByCid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClueService service = (ClueService) ServiceFactory.getService(ClueServiceImpl.class);
        String clueId = request.getParameter("clueId");
        List<Activity> aList = service.getActivityListByCid(clueId);
        response.getWriter().print(JSON.toJSONString(aList));
    }

    private void removeRemark(HttpServletRequest request, HttpServletResponse response) {
    }

    private void editRemark(HttpServletRequest request, HttpServletResponse response) {
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClueService service = (ClueService) ServiceFactory.getService(ClueServiceImpl.class);
        ClueRemark bean = BeanUtil.newBean(ClueRemark.class, request);
        bean.setId(UUIDUtil.getUUID());
        bean.setEditFlag("0");

        boolean flag = service.saveRemark(bean);
        response.getWriter().print(flag ? JSON.toJSONString(bean) : null);
    }

    private void getRemarkListByCid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClueService service = (ClueService) ServiceFactory.getService(ClueServiceImpl.class);
        String clueId = request.getParameter("clueId");
        List<ClueRemark> cList = service.getRemarkListByCid(clueId);
        response.getWriter().print(JSON.toJSONString(cList));
    }


    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ClueService service = (ClueService) ServiceFactory.getService(ClueServiceImpl.class);
        String id = request.getParameter("id");
        Clue c = service.detail(id);
        request.setAttribute("c", c);
        request.getRequestDispatcher("detail.jsp").forward(request, response);
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClueService service = (ClueService) ServiceFactory.getService(ClueServiceImpl.class);
        Map<String, Object> map = getMap(request);
        PageVo<Clue> vo = service.pageList(map);
        response.getWriter().print(JSON.toJSONString(vo));
    }

    private Map<String, Object> getMap(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("fullname", request.getParameter("fullname"));
        map.put("company", request.getParameter("company"));
        map.put("phone", request.getParameter("phone"));
        map.put("source", request.getParameter("source"));
        map.put("owner", request.getParameter("owner"));
        map.put("mphone", request.getParameter("mphone"));
        map.put("state", request.getParameter("state"));
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
        map.put("skipCount", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        return map;
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService service = (UserService) ServiceFactory.getService(UserServiceImpl.class);
        List<User> all = service.findAll();
        response.getWriter().print(JSON.toJSONString(all));
    }
}
