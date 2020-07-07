package com.gylang.excel.db.dbproxy;

import com.gylang.excel.db.context.IWorkBookContext;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.entity.WorkBookInfo;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.handler.IExcelHandler;
import com.gylang.excel.db.handler.IWorkBookCurdHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExcelMapperProxyFactory {

    /**
     * 代理对象集合
     */
    private Map<String, ExcelMapper<?>> proxyExcelMapper;

    private IExcelColValHandler excelColValHandler;
    private IWorkBookCurdHandler<Object> workBookCrudHandler;
    private IExcelHandler excelHandler;
    private IAnnotationExcelTypeHandler annotationExcelTypeHandler;
    private IWorkBookContext workBookContext;
    private List<Class<?>> classList;


    /**
     * 获取代理对象
     *
     * @param clazz 通过实体类获取代理对象
     * @param <T>   代理类实体类型
     * @return 代理对象
     */
    public <T> ExcelMapper<T> getProxy(Class<T> clazz) {
        ExcelMapper<?> excelMapper = proxyExcelMapper.get(clazz.getName());
        return (ExcelMapper<T>) excelMapper;

    }

    /**
     * 初始化代理对象
     *
     * @param excelMapperList 需要创建代理对象的实体类
     */
    public ExcelMapperProxyFactory init(List<Class<?>> excelMapperList) {

        //构建查询处理
        workBookCrudHandler.setExcelColValHandler(excelColValHandler);
        annotationExcelTypeHandler.setIExcelColValHandler(excelColValHandler);
        // workBook缓存
        workBookContext.setAnnotationExcelTypeHandler(annotationExcelTypeHandler);
        workBookContext.setExcelHandler(excelHandler);

        //构建查询处理
        workBookCrudHandler.setExcelColValHandler(excelColValHandler);
        annotationExcelTypeHandler.setIExcelColValHandler(excelColValHandler);
        proxyExcelMapper = new HashMap<>(excelMapperList.size());
        Map<String, Workbook> release = new HashMap<>(excelMapperList.size());
        for (Class<?> clazz : excelMapperList) {
            SheetInfo sheetInfo = new SheetInfo();
            sheetInfo.setClazz(clazz);
            //获取实体类映射workbook
            WorkBookInfo workBookInfo = annotationExcelTypeHandler.getWorkBookInfo(clazz);
            sheetInfo.setWorkBookInfo(workBookInfo);
            //获取workbook对象
            Workbook workBook = release.get(workBookInfo.getExcelName());
            if (null == workBook) {
                workBook = excelHandler.getWorkBook(workBookInfo);
            }
            //获取实体类映射关系
            Sheet targetSheet = annotationExcelTypeHandler.getTargetSheet(workBook, clazz);
            annotationExcelTypeHandler.getSheetInfo(sheetInfo, clazz, targetSheet.getRow(0));
            //初始化代理对象
            ExcelMapperImpl excelMapper = ExcelMapperImpl.init(sheetInfo, workBookCrudHandler, workBookContext);
            //存储代理对象进map
            proxyExcelMapper.put(clazz.getName(), excelMapper);
        }
        //数据获取完毕 释放资源
        for (Workbook value : release.values()) {
            try {
                value.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        release.clear();
        return this;
    }
}
