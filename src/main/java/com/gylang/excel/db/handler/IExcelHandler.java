package com.gylang.excel.db.handler;

import com.gylang.excel.db.entity.WorkBookInfo;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */

public interface IExcelHandler {
    /**
     * 获取workbook
     *
     * @param workBookInfo workBook 信息
     * @return WorkBook 对象
     */
    Workbook getWorkBook(WorkBookInfo workBookInfo);

    /**
     * 写入文件
     *
     * @param workbook 写入的excel对象
     */
    void write(Workbook workbook, WorkBookInfo workBookInfo);


}
