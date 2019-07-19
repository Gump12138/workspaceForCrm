package com.bjpowernode.crm.setting.service;

import com.bjpowernode.crm.setting.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-18
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}
