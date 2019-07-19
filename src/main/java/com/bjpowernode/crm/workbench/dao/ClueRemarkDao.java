package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    int save(ClueRemark bean);

    List<ClueRemark> getRemarkListByCid(String clueId);
}
