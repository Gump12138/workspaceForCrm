package com.bjpowernode.crm.setting.exception;

/**
 * 登陆异常
 * Author: 甘明波
 * 2019-07-15
 */
public class LoginException extends RuntimeException {

    public LoginException() {
        super("登陆异常");
    }

    public LoginException(String message) {
        super("登陆异常：" + message);
    }

    //账户密码失效
    public static class ActAndPwdException extends LoginException {
        public ActAndPwdException() {
            super("用户或密码错误，请重新输入");
        }
    }

    //账户失效
    public static class ExpireException extends LoginException {
        public ExpireException() {
            super("账户已失效，请重新申请");
        }
    }

    //账户锁定
    public static class LockException extends LoginException {
        public LockException() {
            super("账户已被锁定，请联系管理人员");
        }
    }

    //登陆ip异常
    public static class AllowIpException extends LoginException {
        public AllowIpException() {
            super("当前账户登陆IP，不在允许登陆的范围内");
        }
    }

}
