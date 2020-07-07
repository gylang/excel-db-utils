package com.gylang.excel.db.handler;

import com.gylang.excel.db.entity.ColHeaderInfo;
import com.gylang.excel.db.entity.SheetInfo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据处理接口
 *
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public interface IExcelColValHandler {

    /**
     * 转字符串
     *
     * @param cell 单元格
     * @return 字符串
     */
    String toStr(Cell cell);

    /**
     * 转字符串
     *
     * @param cell 单元格
     * @return 字符串
     */
    Date toDate(Cell cell);

    /**
     * 转字符串
     *
     * @param cell 单元格
     * @return 字符串
     */
    Integer toInteger(Cell cell);

    /**
     * 转字符串
     *
     * @param cell 单元格
     * @return 字符串
     */
    Float toFloat(Cell cell);

    /**
     * 转字符串
     *
     * @param cell 单元格
     * @return 字符串
     */
    Double toDouble(Cell cell);


    /**
     * 转BigDecimal
     *
     * @param cell 单元格
     * @return BigDecimal
     */
    BigDecimal toBigDecimal(Cell cell);

    /**
     * 获取单元格的值 转成java数据类型
     *
     * @param colHeaderInfo 实体类 与excel映射关系
     * @param cell          单元格
     * @return java数据类型
     */
    Object getDataForExcelColType(ColHeaderInfo colHeaderInfo, Cell cell);

    /**
     * 给对象赋值
     *
     * @param sheetInfo sheetInfo数据
     * @param target    待赋值对象
     * @param row       行数据
     */
    void setData(Row row, Object target, SheetInfo sheetInfo);


}
