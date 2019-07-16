package com.bjpowernode.crm.setting.dao;

import com.bjpowernode.crm.setting.domain.User;

import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-13
 */
public interface UserDao {
    User login(Map<String,String> map);

    List<User> findAll();
}
