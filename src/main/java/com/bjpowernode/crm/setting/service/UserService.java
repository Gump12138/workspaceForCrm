package com.bjpowernode.crm.setting.service;

import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.setting.exception.LoginException;

import java.util.List;
import java.util.Map;

/**
 * Author: 甘明波
 * 2019-07-13
 */
public interface UserService {
    User login(Map<String, String> map) throws LoginException;

    List<User> findAll();
}
