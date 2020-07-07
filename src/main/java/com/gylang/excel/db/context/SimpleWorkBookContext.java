package com.gylang.excel.db.context;

import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelHandler;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public class SimpleWorkBookContext implements IWorkBookContext {
    private IExcelHandler excelHandler;
    private IAnnotationExcelTypeHandler annotationExcelTypeHandler;
    private Map<String, Workbook> workBookMap = new HashMap<>();

    @Override
    public Sheet getSheet(SheetInfo sheetInfo) {
        Workbook workBook = excelHandler.getWorkBook(sheetInfo.getWorkBookInfo());
        workBookMap.put(sheetInfo.getWorkBookInfo().getExcelName(), workBook);
        return annotationExcelTypeHandler.getTargetSheet(workBook, sheetInfo.getClazz());
    }

    @Override
    public void release(SheetInfo sheetInfo) {
        Workbook remove = workBookMap.remove(sheetInfo.getWorkBookInfo().getExcelName());
        if (null != remove) {
            try {
                remove.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setExcelHandler(IExcelHandler excelHandler) {
        this.excelHandler = excelHandler;
    }

    @Override
    public void setAnnotationExcelTypeHandler(IAnnotationExcelTypeHandler annotationExcelTypeHandler) {
        this.annotationExcelTypeHandler = annotationExcelTypeHandler;
    }


}
