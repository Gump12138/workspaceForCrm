package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PageVo;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-18
 */
public interface ClueService {
    PageVo<Clue> pageList(Map<String,Object> map);

    Clue detail(String id);

    boolean saveRemark(ClueRemark bean);

    List<ClueRemark> getRemarkListByCid(String clueId);

    List<Activity> getActivityListByCid(String clueId);

    boolean unbound(ClueActivityRelation relation);

    boolean bound(ClueActivityRelation relation);
}
