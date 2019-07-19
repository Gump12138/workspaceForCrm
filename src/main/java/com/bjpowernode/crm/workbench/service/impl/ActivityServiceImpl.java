package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.setting.dao.UserDao;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao remarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean add(Activity activity) {
        return activityDao.save(activity) == 1;
    }

    @Override
    public PageVo<Activity> search(Map<String, Object> map) {
        List<Activity> list = activityDao.getActivityListByCondition(map);
        int pageCount = activityDao.getTotalByCondition(map);
        PageVo<Activity> vo = new PageVo<>();
        vo.setDataList(list).setPageCount(pageCount);
        return vo;
    }

    @Override
    public boolean del(String[] ids) {
        //备注预计的删除条目
        int mustRDelNum = remarkDao.countByIds(ids);
        System.out.println("备注预计的删除条目：" + mustRDelNum);
        //备注的删除条目
        int rDelNum = remarkDao.del(ids);
        System.out.println("备注的删除条目：" + rDelNum);
        //预计的市场活动删除条目
        int mustaDelNum = ids.length;
        System.out.println("预计的市场活动删除条目：" + mustaDelNum);
        //市场活动的删除条目
        int aDelNum = activityDao.delete(ids);
        System.out.println("市场活动的删除条目：" + aDelNum);
        return mustRDelNum == rDelNum && mustaDelNum == aDelNum;
    }

    @Override
    public Map<String, Object> selectOne(String id) {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userDao.findAll();
        Activity activity = activityDao.getById(id);
        if (userList != null && userList.size() != 0) {
            map.put("userList", userList);
        }
        if (activity != null) {
            map.put("activity", activity);
        }
        map.put("success", map.size() == 2);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        return activityDao.update(activity) == 1;
    }

    @Override
    public Activity detail(String id) {
        Activity detail = activityDao.detail(id);
        detail.setId(id);
        return detail;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String id) {
        return remarkDao.getRemarkListByAid(id);
    }

    @Override
    public boolean saveRemark(ActivityRemark remark) {
        return remarkDao.saveRemark(remark) == 1;
    }

    @Override
    public boolean updateRemark(ActivityRemark remark) {
        return remarkDao.updateRemark(remark) == 1;
    }

    @Override
    public boolean removeRemark(String id) {
        return remarkDao.del(new String[]{id}) == 1;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map) {
        return activityDao.getActivityListByNameAndNotByClueId(map);
    }
}
