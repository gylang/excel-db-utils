package com.gylang.excel.db.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理json序列
 *
 * @author gylang,
 * date 2020/4/23,
 * @version 1.0
 */
public class JsonUtils {


    /**
     * 对象序列化Json字符串
     *
     * @param o 待转对象
     * @return json字符串
     */
    public static String toJson(Object o) {

        return JSON.toJSONString(o);
    }

    /**
     * 对json进行校验
     *
     * @param json 待转对象
     * @return json字符串
     */
    public static boolean isValid(String json) {

        return JSON.isValid(json);
    }


    /**
     * json反序列化成对象
     *
     * @param json     待转json
     * @param <T>   目标类
     * @return 目标对象
     */
    public static <T> T toObject(String json, TypeReference<T> type) {

        return JSON.parseObject(json, type);
    }


    /**
     * json反序列化成对象
     *
     * @param str   待转字符串
     * @param clazz 目标类
     * @param <T>   目标类
     * @return 目标对象
     */
    public static <T> T toObject(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }

    /**
     * json反序列化成对象
     *
     * @param str   待转字符串
     * @param clazz 目标类
     * @param <T>   目标类
     * @return 目标对象
     */
    public static <T> List<T> toList(String str, Class<T> clazz) {

        return JSON.parseArray(str, clazz);
    }

    /**
     * json反序列化成对象
     *
     * @param list   待转list
     * @param clazz 目标类
     * @param <T>   目标类
     * @return 目标对象
     */
    public static <T> List<T> toList(List<String> list, Class<T> clazz) {

        return list.stream()
                .map((str) -> JSON.parseObject(str, clazz))
                .collect(Collectors.toList());
    }
}
