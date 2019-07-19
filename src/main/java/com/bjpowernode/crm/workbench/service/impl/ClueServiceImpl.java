package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.dao.ClueActivityRelationDao;
import com.bjpowernode.crm.workbench.dao.ClueDao;
import com.bjpowernode.crm.workbench.dao.ClueRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-18
 */
public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueRemarkDao remarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private ClueActivityRelationDao relationDao =
            SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    @Override
    public PageVo<Clue> pageList(Map<String, Object> map) {
        List<Clue> cList = clueDao.pageList(map);
        PageVo<Clue> vo = new PageVo<>();
        vo
                .setPageCount(cList.size())
                .setDataList(cList);

        return vo;
    }

    @Override
    public Clue detail(String id) {
        return clueDao.detail(id);
    }

    @Override
    public List<ClueRemark> getRemarkListByCid(String clueId) {
        return remarkDao.getRemarkListByCid(clueId);
    }

    @Override
    public boolean saveRemark(ClueRemark bean) {
        return remarkDao.save(bean) == 1;
    }

    @Override
    public List<Activity> getActivityListByCid(String clueId) {
        return relationDao.getActivityListByCid(clueId);
    }

    @Override
    public boolean unbound(ClueActivityRelation relation) {
        return relationDao.unbound(relation) == 1;
    }

    @Override
    public boolean bound(ClueActivityRelation relation) {
        return relationDao.bound(relation) == 1;
    }

}
