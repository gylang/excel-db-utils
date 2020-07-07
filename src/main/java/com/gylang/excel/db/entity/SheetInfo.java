package com.gylang.excel.db.entity;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * excel的sheet表与实体类映射关系
 *
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
@Data
public class SheetInfo {

    /**
     * excel信息
     */
    private WorkBookInfo workBookInfo;

    /**
     * sheet名
     */
    private String sheetName;

    /**
     * 实体类类型
     */
    private Class<?> clazz;

    /**
     * 实体映射 到excel字段的信息 key 实体类对应的属性名 value excel的信息
     */
    private Map<String, ColHeaderInfo> target = new HashMap<>();


}
