package com.gylang.excel.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * sheet 行头信息 / 字段名成
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ColHeaderInfo {


    private String colName;

    private Integer length;

    private Class<?> type;

    private String typeName;

    private Integer index;
}
