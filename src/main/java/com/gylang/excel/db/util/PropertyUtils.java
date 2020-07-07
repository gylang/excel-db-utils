package com.gylang.excel.db.util;

import com.gylang.excel.db.myassert.MyAssert;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 对象赋值
 *
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public class PropertyUtils {


    public static void setValue(Object obj, String key, Object value) {

        ReflectionUtils.invoke(obj, setterName(key), value);
    }

    public static Object getValue(Object obj, String key) {

        return ReflectionUtils.invoke(obj, getterName(key));
    }


    public static Map<String, Object> toMap(Object source) {

        MyAssert.notNull(source, "获取对象属性失败, 对象为空");
        Field[] fields = source.getClass().getDeclaredFields();
        Map<String, Object> property = new HashMap<>();
        for (Field field : fields) {
            Object value = getValue(source, field.getName());
            if (null != value) {

                property.put(field.getName(), value);

            }
        }

        return property;
    }


    /**
     * 获取setter名字
     *
     * @param key 字段名
     * @return setter方法名
     */
    public static String setterName(String key) {

        return "set" + StringUtils.upperCase(key.charAt(0) + "") + key.substring(1);
    }

    /**
     * 获取setter名字
     *
     * @param key 字段名
     * @return setter方法名
     */
    public static String getterName(String key) {

        return "get" + StringUtils.upperCase(key.charAt(0) + "") + key.substring(1);
    }
}
