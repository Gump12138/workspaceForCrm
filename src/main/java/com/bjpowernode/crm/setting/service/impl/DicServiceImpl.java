package com.bjpowernode.crm.setting.service.impl;

import com.bjpowernode.crm.setting.dao.DicTypeDao;
import com.bjpowernode.crm.setting.dao.DicValueDao;
import com.bjpowernode.crm.setting.domain.DicType;
import com.bjpowernode.crm.setting.domain.DicValue;
import com.bjpowernode.crm.setting.service.DicService;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-18
 */
public class DicServiceImpl implements DicService {
    private DicTypeDao typeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao valueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> typeList = typeDao.getTypeList();
        for (DicType type : typeList) {
            List<DicValue> valueList = valueDao.getValueListByCode(type.getCode());
            map.put(type.getCode() + "List", valueList);
        }
        return map;
    }
}
