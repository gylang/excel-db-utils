package com.gylang.excel.db.dbproxy;

import com.gylang.excel.db.context.IWorkBookContext;
import com.gylang.excel.db.context.SimpleWorkBookContext;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.handler.IExcelHandler;
import com.gylang.excel.db.handler.IWorkBookCrudHandler;
import com.gylang.excel.db.handler.impl.SimpleExcelColHandlerImpl;
import com.gylang.excel.db.handler.impl.SimpleExcelHandler;
import com.gylang.excel.db.handler.impl.SimpleWorkBookCrudHandlerImpl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    private IExcelColValHandler excelColValHandler;
    private IWorkBookCrudHandler workBookCrudHandler;
    private IExcelHandler simpleExcelHandler;
    private IAnnotationExcelTypeHandler annotationExcelTypeHandler;
    private IWorkBookContext workBookContext;
    private List<Class<?>> classList;

    public  ExcelMapperProxy buildProxy() {
        //构建查询处理
        workBookCrudHandler.setIExcelColValHandler(excelColValHandler);
        annotationExcelTypeHandler.setIExcelColValHandler(excelColValHandler);

        // workBook缓存
        workBookContext.setAnnotationExcelTypeHandler(annotationExcelTypeHandler);
        workBookContext.setExcelHandler(simpleExcelHandler);

        //构建代理对象
        ExcelMapperProxy excelMapperProxy = new ExcelMapperProxy();
        excelMapperProxy.setAnnotationExcelTypeHandler(annotationExcelTypeHandler);
        excelMapperProxy.setWorkBookCrudHandler(workBookCrudHandler);
        excelMapperProxy.setWorkBookContext(workBookContext);
        excelMapperProxy.setExcelHandler(simpleExcelHandler);
        excelMapperProxy.init(classList);

        return excelMapperProxy;
    }
}
