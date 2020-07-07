package com.gylang.excel.db.util;

import com.gylang.excel.db.entity.ColHeaderInfo;
import com.gylang.excel.db.myassert.MyAssert;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public class FieldType2ExcelColTypeUtils {

    public static final Map<String, Class<?>> EXCEL_TYPE = new HashMap<>(6);

    static {
        EXCEL_TYPE.put(String.class.getName(), String.class);
        EXCEL_TYPE.put(Integer.class.getName(), Integer.class);
        EXCEL_TYPE.put(Float.class.getName(), Float.class);
        EXCEL_TYPE.put(Double.class.getName(), Double.class);
        EXCEL_TYPE.put(Date.class.getName(), Date.class);
        EXCEL_TYPE.put(BigDecimal.class.getName(), BigDecimal.class);
    }

    public static ColHeaderInfo toExcelColType(String name, Class<?> type, Integer index, Integer length) {

        Class<?> excelColType = EXCEL_TYPE.get(type.getName());
        MyAssert.notNull(excelColType, "当前不支持此类型数据的映射: " + type.getName());

        return ColHeaderInfo.builder()
                .colName(name)
                .index(index)
                .type(excelColType)
                .typeName(type.getTypeName())
                .length(length)
                .build();
    }
}
