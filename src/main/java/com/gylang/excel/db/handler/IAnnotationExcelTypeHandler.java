package com.gylang.excel.db.handler;

import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.entity.WorkBookInfo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 处理实体类映射
 *
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public interface IAnnotationExcelTypeHandler {


    /**
     * 获取excel sheet 第一行表头信息
     * 获取实体类 与 excel的映射关系 key: 字段名 value: 列下标
     *
     * @param sheetInfo 对象映射关系赋值对象
     * @param source    映射实体类
     * @param row       第一行数据
     */
    void getSheetInfo(SheetInfo sheetInfo, Class<?> source, Row row);

    /**
     * 获取workBookInfo
     * 存储了workbook的路径
     *
     * @param source 实体类映射对象
     * @return WorkBookInfo 实现excel信息
     */
    WorkBookInfo getWorkBookInfo(Class<?> source);

    /**
     * 获取excel sheet 通过实体类映射获取指定的sheet
     *
     * @param workbook 对象映射关系赋值对象
     * @param target   映射实体类
     * @return 获取区第一行的
     */
    Sheet getTargetSheet(Workbook workbook, Class<?> target);

    /**
     * 获取excel数据处理实现类
     *
     * @return 数据处理实现类
     */
    IExcelColValHandler getIExcelColValHandler();

    /**
     * 设置数据处理类
     *
     * @param excelColValHandler 数据处理实现类
     */
    void setIExcelColValHandler(IExcelColValHandler excelColValHandler);


}
