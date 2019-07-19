package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public interface ActivityService {
    boolean add(Activity activity);

    PageVo<Activity> search(Map<String, Object> map);

    boolean del(String[] ids);

    Map<String, Object> selectOne(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByAid(String id);

    boolean saveRemark(ActivityRemark remark);

    boolean updateRemark(ActivityRemark remark);

    boolean removeRemark(String id);

    List<Activity> getActivityListByNameAndNotByClueId(Map<String, String> map);
}
