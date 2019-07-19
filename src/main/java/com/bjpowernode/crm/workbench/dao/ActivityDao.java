package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public interface ActivityDao {
    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    int countByIds(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    int save(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);
}
