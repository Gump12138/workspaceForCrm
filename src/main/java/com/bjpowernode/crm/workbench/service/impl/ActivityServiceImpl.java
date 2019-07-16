package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public class ActivityServiceImpl implements ActivityService {
    private static ActivityDao dao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    @Override
    public void add(Activity activity) {
        dao.add(activity);
    }
}
