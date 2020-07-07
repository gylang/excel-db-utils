package com.gylang.excel.db.entity;

import com.gylang.excel.db.enums.ExcelColType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

/**
 * sheet 行头信息
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
