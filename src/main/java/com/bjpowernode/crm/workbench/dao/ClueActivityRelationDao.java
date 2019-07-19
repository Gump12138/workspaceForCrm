package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;
import java.util.Map;

public interface ClueActivityRelationDao {
    List<Activity> getActivityListByCid(String clueId);

    int unbound(ClueActivityRelation clueActivityRelation);

    int bound(ClueActivityRelation clueActivityRelation);
}
