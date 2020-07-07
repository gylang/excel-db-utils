package com.gylang.excel.db.entity;

import com.gylang.excel.db.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
@Excel(path = "F:\\文档\\", name = "test (2)")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    private Integer id;
    private String name;
    private String desc;
    private Date createTime;
}
