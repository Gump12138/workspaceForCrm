package com.bjpowernode.crm.utils;

import com.bjpowernode.crm.setting.exception.LoginException;

/**
 * Author: 甘明波
 * 2019-07-15
 */
public class ExceptionFactory {
    private static LoginException.ActAndPwdException actAndPwdException = new LoginException.ActAndPwdException();
    private static LoginException.ExpireException expireException = new LoginException.ExpireException();
    private static LoginException.LockException lockException = new LoginException.LockException();
    private static LoginException.AllowIpException allowIpException = new LoginException.AllowIpException();

    private ExceptionFactory() {
    }

    public static LoginException.ActAndPwdException getActAndPwdException() {
        return actAndPwdException;
    }

    public static LoginException.ExpireException getExpireException() {
        return expireException;
    }

    public static LoginException.LockException getLockException() {
        return lockException;
    }

    public static LoginException.AllowIpException getAllowIpException() {
        return allowIpException;
    }
}
