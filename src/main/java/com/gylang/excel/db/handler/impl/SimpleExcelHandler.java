package com.gylang.excel.db.handler.impl;

import com.gylang.excel.db.entity.WorkBookInfo;
import com.gylang.excel.db.handler.IExcelHandler;
import com.gylang.excel.db.myassert.MyAssert;
import com.gylang.excel.db.util.ExcelUtils;
import com.gylang.excel.db.util.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.List;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public class SimpleExcelHandler implements IExcelHandler {


    @Override
    public Workbook getWorkBook(WorkBookInfo workBookInfo) {

        MyAssert.notNull(workBookInfo, "workBookInfo为空， 读取excel失败");
        //获取目录下的文件 通过文件名 获取excel
        List<String> excelName = FileUtils.readPath(workBookInfo.getExcelPath());
        for (String name : excelName) {
            if (name.contains(workBookInfo.getExcelName())) {
                try {
                    workBookInfo.setAbsolutePath(name);
                    return ExcelUtils.getExcel(name);
                } catch (IOException e) {
                    MyAssert.fail("读取excel文件出错：文件名：" + name);
                }
            }
        }
        MyAssert.fail("读取excel文件出错：未找到相应的excel文件，excelPath = " + workBookInfo.getExcelPath()
                + ", excelName = " + workBookInfo.getExcelName());
        return null;
    }

    @Override
    public void write(Workbook workbook, WorkBookInfo workBookInfo) {

        ExcelUtils.write(workbook, workBookInfo.getAbsolutePath());
    }
}
