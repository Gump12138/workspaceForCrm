package com.bjpowernode.crm.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * ——————————————————————————————
 * 是什么：
 * 在servlet中从request域对象中取出指定的类的各个属性，
 * 同时把他们封装成实体类对象返回
 * ——————————————————————————————
 * 作用：
 * 简化了从request中取参数并组合成实例对象的开发工作
 * ——————————————————————————————
 * 要求：
 * 前端的表单域中的name属性必须与后端实体类完全相等
 * 只能兼容
 * java.lang.String，
 * java.util.Date(要求前端按照标准格式传递),
 * java.util.Integer,
 * java.util.Double类型
 * 其他的还要慢慢完善
 * Author: 甘明波
 * 2019-07-15
 */
public class BeanUtil {
    private BeanUtil() {
    }

    public static <T> T newBean(Class<T> tClass, HttpServletRequest request) {
        T instance = null;
        try {
            instance = tClass.newInstance();
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                String parameter = request.getParameter(field.getName());
                Class<?> fieldType = field.getType();
                Object value = parameter;
                if (String.class == fieldType || Date.class == fieldType) {
                    //无需转换类型,直接赋值
                } else if (Integer.class == fieldType) {
                    value = Integer.parseInt(parameter);
                } else if (Double.class == fieldType) {
                    value = Double.parseDouble(parameter);
                }
                field.setAccessible(true);
                field.set(instance, value);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
