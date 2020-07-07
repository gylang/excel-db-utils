package com.gylang.excel.db.dbproxy;

import com.gylang.excel.db.context.IWorkBookContext;
import com.gylang.excel.db.dbproxy.ExcelMapper;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.entity.WorkBookInfo;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelHandler;
import com.gylang.excel.db.handler.IWorkBookCrudHandler;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import sun.font.LayoutPathImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public class ExcelMapperProxy {

    private Map<String, ExcelMapper<?>> proxyExcelMapper;
    @Setter
    private IAnnotationExcelTypeHandler annotationExcelTypeHandler;
    @Setter
    private IExcelHandler excelHandler;
    @Setter
    private IWorkBookCrudHandler workBookCrudHandler;
    @Setter
    private IWorkBookContext workBookContext;


    public <T> ExcelMapper<T> getProxy(Class<T> clazz) {
        ExcelMapper<?> excelMapper = proxyExcelMapper.get(clazz.getName());
        return (ExcelMapper<T>) excelMapper;

    }

    public void init(List<Class<?>> excelMapperList) {

        proxyExcelMapper = new HashMap<>(excelMapperList.size());
        Map<String, Workbook> release = new HashMap<>();
        for (Class<?> clazz : excelMapperList) {
            SheetInfo sheetInfo = new SheetInfo();
            sheetInfo.setClazz(clazz);
            WorkBookInfo workBookInfo = annotationExcelTypeHandler.getWorkBookInfo(clazz);
            sheetInfo.setWorkBookInfo(workBookInfo);
            Workbook workBook = release.get(workBookInfo.getExcelName());
            if (null == workBook) {
                workBook = excelHandler.getWorkBook(workBookInfo);
            }
            Sheet targetSheet = annotationExcelTypeHandler.getTargetSheet(workBook, clazz);
            annotationExcelTypeHandler.getSheetInfo(sheetInfo, clazz, targetSheet.getRow(0));
            ExcelMapperImpl excelMapper = ExcelMapperImpl.init(sheetInfo, workBookCrudHandler, workBookContext);
            proxyExcelMapper.put(clazz.getName(), excelMapper);

        }
        //释放资源
        for (Workbook value : release.values()) {
            try {
                value.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
