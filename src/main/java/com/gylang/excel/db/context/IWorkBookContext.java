package com.gylang.excel.db.context;

import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelHandler;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 存储workbook对象 可以自定义workbook对象的缓存策略 简单实现就是直接调用 然后释放资源
 *
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public interface IWorkBookContext {


    /**
     * 获取sheet
     *
     * @param sheetInfo 用户获取指定sheet的信息
     * @return Sheet
     */
    Sheet getSheet(SheetInfo sheetInfo);

    /**
     * 释放资源
     *
     * @param sheetInfo sheet信息 用于判断释放哪一个workbook对象
     */
    void release(SheetInfo sheetInfo);

    /**
     * 设置excel处理类
     *
     * @param excelHandler excel处理类
     */
    void setExcelHandler(IExcelHandler excelHandler);

    /**
     * 注解处理器
     *
     * @param annotationExcelTypeHandler 注解处理器
     */
    void setAnnotationExcelTypeHandler(IAnnotationExcelTypeHandler annotationExcelTypeHandler);
}
