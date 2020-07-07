package com.gylang.excel.db;

import com.gylang.excel.db.context.SimpleWorkBookContext;
import com.gylang.excel.db.dbproxy.ExcelMapper;
import com.gylang.excel.db.dbproxy.ExcelMapperProxy;
import com.gylang.excel.db.dbproxy.ExcelMapperProxyFactory;
import com.gylang.excel.db.entity.Test;
import com.gylang.excel.db.handler.impl.SimpleExcelColHandlerImpl;
import com.gylang.excel.db.handler.impl.SimpleAnnotationExcelTypeHandlerImpl;
import com.gylang.excel.db.handler.impl.SimpleExcelHandler;
import com.gylang.excel.db.handler.impl.SimpleWorkBookCrudHandlerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public class TestMapper {

    public static void main(String[] args) {
        //准备资源
        //excel cell -> 转化成java属性值
        SimpleExcelColHandlerImpl simpleExcelColHandler = new SimpleExcelColHandlerImpl();
        //处理增删查改
        SimpleWorkBookCrudHandlerImpl simpleIWorkBookCrudHandler = new SimpleWorkBookCrudHandlerImpl();
        //处理excel操作
        SimpleExcelHandler simpleExcelHandler = new SimpleExcelHandler();
        //javabean映射excel相关处理
        SimpleAnnotationExcelTypeHandlerImpl simpleIAnnotationExcelTypeHandler = new SimpleAnnotationExcelTypeHandlerImpl();
        //缓存相关 workbook对象统一管理
        SimpleWorkBookContext simpleWorkBookContext = new SimpleWorkBookContext();
        //代理mapper (通过实体类创建mapper) 当前没有通过脚本代码,或者额外的查询方式 的方式进行查询
        List<Class<?> > classList = new ArrayList<>();
        classList.add(Test.class);

        //构建代理对象
        ExcelMapperProxy excelMapperProxy = ExcelMapperProxyFactory.builder()
                .annotationExcelTypeHandler(simpleIAnnotationExcelTypeHandler)
                .simpleExcelHandler(simpleExcelHandler)
                .excelColValHandler(simpleExcelColHandler)
                .workBookContext(simpleWorkBookContext)
                .workBookCrudHandler(simpleIWorkBookCrudHandler)
                .classList(classList)
                .build().buildProxy();

        //通过代理类获取mapper代理对象
        ExcelMapper<Test> testExcelMapper = excelMapperProxy.getProxy(Test.class);
        //查询
        System.out.println(testExcelMapper.selectList(Test.builder().build()));
    }
}
