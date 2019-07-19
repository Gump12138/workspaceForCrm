package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * Author: 甘明波
 * 2019-07-16
 */
public interface ActivityRemarkDao {

    int del(String[] ids);

    int countByIds(String[] ids);

    List<ActivityRemark> getRemarkListByAid(String id);

    int saveRemark(ActivityRemark activityRemark);

    int updateRemark(ActivityRemark activityRemark);

    boolean removeRemark(String id);
}
