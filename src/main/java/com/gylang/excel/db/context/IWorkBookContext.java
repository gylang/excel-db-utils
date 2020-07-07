package com.gylang.excel.db.context;

import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelHandler;
import org.apache.poi.ss.usermodel.Sheet;

/**
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
     */
    void release(SheetInfo sheetInfo);

    /**
     * 设置excel处理类
     * @param excelHandler excel处理类
     */
    void setExcelHandler(IExcelHandler excelHandler);

    /**
     * 注解
     * @param annotationExcelTypeHandler
     */
    void setAnnotationExcelTypeHandler(IAnnotationExcelTypeHandler annotationExcelTypeHandler);
}
