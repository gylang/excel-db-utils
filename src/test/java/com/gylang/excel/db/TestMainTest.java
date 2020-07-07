package com.gylang.excel.db;


import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.entity.Test;
import com.gylang.excel.db.entity.WorkBookInfo;
import com.gylang.excel.db.excep.BaseException;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.handler.IExcelHandler;
import com.gylang.excel.db.handler.IWorkBookCrudHandler;
import com.gylang.excel.db.handler.impl.SimpleAnnotationExcelTypeHandlerImpl;
import com.gylang.excel.db.handler.impl.SimpleExcelColHandlerImpl;
import com.gylang.excel.db.handler.impl.SimpleExcelHandler;
import com.gylang.excel.db.handler.impl.SimpleWorkBookCrudHandlerImpl;
import com.gylang.excel.db.util.JsonUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public class TestMainTest {

    public static void main(String[] args) {

        try {
            //excel数据处理
            IExcelColValHandler IExcelColValHandler = new SimpleExcelColHandlerImpl();

            //excel 实体类映射
            IAnnotationExcelTypeHandler IAnnotationExcelTypeHandler = new SimpleAnnotationExcelTypeHandlerImpl();
            IAnnotationExcelTypeHandler.setIExcelColValHandler(IExcelColValHandler);

            //curd 操作
            IWorkBookCrudHandler IWorkBookCrudHandler = new SimpleWorkBookCrudHandlerImpl();
            IWorkBookCrudHandler.setIExcelColValHandler(IExcelColValHandler);

            //获取excel 写入excel
            IExcelHandler IExcelHandler = new SimpleExcelHandler();

            //获取实体类映射关系
            Test test = new Test();
            //获取注解对应的excel
            WorkBookInfo workBookInfo = IAnnotationExcelTypeHandler.getWorkBookInfo(Test.class);
            //读取excel
            Workbook workBook = IExcelHandler.getWorkBook(workBookInfo);
            //获取 sheet
            SheetInfo sheetInfo = new SheetInfo();
            Sheet targetSheet = IAnnotationExcelTypeHandler.getTargetSheet(workBook, Test.class);
            //获取实体映射到sheet的新
            sheetInfo.setWorkBookInfo(workBookInfo);
            IAnnotationExcelTypeHandler.getSheetInfo(sheetInfo, Test.class, targetSheet.getRow(0));

//            List<Test> tests = workBookCrudHandler.selectList(targetSheet, sheetInfo, test);
//            System.out.println(JsonUtils.toJson(tests));

            //按id查询
//            List<Test> testList = workBookCrudHandler.selectList(targetSheet, sheetInfo, Test.builder().id(1).build());
//            System.out.println(testList);

            //增加一行
//            Test insertData = Test.builder()
//                    .id(12)
//                    .createTime(new Date())
//                    .desc("gaga")
//                    .name("dwada")
//                    .build();
//            workBookCrudHandler.insert(targetSheet, sheetInfo, insertData);
//            List<Test> tests = workBookCrudHandler.selectList(targetSheet, sheetInfo, test);
//            System.out.println(JsonUtils.toJson(tests));
            //按条件修改
            Test query = Test.builder()
                    .id(2)
                    .build();

            Test updateData = Test.builder()
                    .id(15)
                    .build();

            IWorkBookCrudHandler.updateByExample(targetSheet, sheetInfo, query, updateData);
            List<Test> tests = IWorkBookCrudHandler.selectList(targetSheet, sheetInfo, test);
            System.out.println(JsonUtils.toJson(tests));
            IExcelHandler.write(workBook, workBookInfo);
        } catch (BaseException e) {
            System.out.println(e.getCode());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}