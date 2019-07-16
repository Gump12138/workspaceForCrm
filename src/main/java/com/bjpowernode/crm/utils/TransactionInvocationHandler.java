package com.bjpowernode.crm.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.bjpowernode.crm.setting.exception.LoginException;
import org.apache.ibatis.session.SqlSession;

public class TransactionInvocationHandler implements InvocationHandler {

    private Object target;

    public TransactionInvocationHandler(Class target) {

        try {
            this.target = target.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        SqlSession session = null;

        Object obj = null;

        try {
            session = SqlSessionUtil.getSqlSession();

            obj = method.invoke(target, args);

            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            //如果是登陆异常，继续往上抛
            if (LoginException.class == e.getCause().getClass().getSuperclass()) {
                throw e.getCause();
            } else {
                e.printStackTrace();
            }
        } finally {
            SqlSessionUtil.close(session);
        }
        return obj;
    }

    public Object getProxy() {

        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);

    }

}











































