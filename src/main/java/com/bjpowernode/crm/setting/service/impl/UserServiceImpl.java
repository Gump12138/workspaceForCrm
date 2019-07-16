package com.bjpowernode.crm.setting.service.impl;

import com.bjpowernode.crm.setting.dao.UserDao;
import com.bjpowernode.crm.setting.domain.User;
import com.bjpowernode.crm.setting.exception.LoginException;
import com.bjpowernode.crm.setting.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.ExceptionFactory;
import com.bjpowernode.crm.utils.SqlSessionUtil;

import java.util.List;
import java.util.Map;


/**
 * Author: 甘明波
 * 2019-07-13
 */
public class UserServiceImpl implements UserService {
    private UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(Map<String, String> map) throws LoginException {
        User user = dao.login(map);
        if (user == null) {
            throw ExceptionFactory.getActAndPwdException();
        }
        //判断是否过期
        String userExpireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (sysTime.compareTo(userExpireTime) < 0) {
            throw ExceptionFactory.getExpireException();
        }
        //判断是否锁上了
        if ("0".equals(user.getLockState())) {
            throw ExceptionFactory.getLockException();
        }
        //判断是否在允许ip范围内
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(map.get("remoteIp"))) {
            throw ExceptionFactory.getAllowIpException();
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }
}
