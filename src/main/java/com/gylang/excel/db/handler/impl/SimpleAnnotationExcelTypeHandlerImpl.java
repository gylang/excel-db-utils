package com.gylang.excel.db.handler.impl;

import com.gylang.excel.db.annotation.Excel;
import com.gylang.excel.db.annotation.ExcelField;
import com.gylang.excel.db.entity.ColHeaderInfo;
import com.gylang.excel.db.entity.DefaultType;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.entity.WorkBookInfo;
import com.gylang.excel.db.excep.ExcelTypeException;
import com.gylang.excel.db.handler.IAnnotationExcelTypeHandler;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.myassert.MyAssert;
import com.gylang.excel.db.util.FieldType2ExcelColTypeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public class SimpleAnnotationExcelTypeHandlerImpl implements IAnnotationExcelTypeHandler {

    private IExcelColValHandler IExcelColValHandler;




    @Override
    public void getSheetInfo(SheetInfo sheetInfo, Class<?> source, Row row) {

        Excel annotation = source.getAnnotation(Excel.class);
        sheetInfo.setSheetName(StringUtils.isNotBlank(annotation.sheetName()) ? annotation.sheetName() : source.getSimpleName());

        Field[] fields = source.getDeclaredFields();
        Map<String, Integer> cellNameIndexMap = new HashMap<>();
        Iterator<Cell> iterator = row.iterator();
        //获取字段名
        while (iterator.hasNext()) {
            Cell cell = iterator.next();
            cellNameIndexMap.put(cell.getStringCellValue(), cell.getColumnIndex());
        }

        //获取实体类属性 对应的字段位置
        for (Field field : fields) {
            ExcelField excelField = field.getAnnotation(ExcelField.class);
            if (null == excelField) {
                //默认方式 使用属性名映射
                String name = field.getName();
                ColHeaderInfo colHeaderInfo = FieldType2ExcelColTypeUtils.toExcelColType(field.getName(), field.getType(), cellNameIndexMap.get(name), -1);
                sheetInfo.getTarget().put(name, colHeaderInfo);
            } else {
                Class<?> type = DefaultType.class == excelField.type() ? field.getType() : excelField.type();
                //注解方式
                ColHeaderInfo colHeaderInfo = FieldType2ExcelColTypeUtils.toExcelColType(excelField.name(), type, cellNameIndexMap.get(excelField.name()), -1);
                sheetInfo.getTarget().put(field.getName(), colHeaderInfo);

            }
        }
    }

    @Override
    public WorkBookInfo getWorkBookInfo(Class<?> source) {

        Excel excel = source.getAnnotation(Excel.class);
        WorkBookInfo workBookInfo = new WorkBookInfo();
        workBookInfo.setExcelPath(excel.path());
        workBookInfo.setExcelName(excel.excelName());

        return workBookInfo;
    }

    @Override
    public Sheet getTargetSheet(Workbook workbook, Class<?> target) {

        MyAssert.notNull(workbook, "excel表不存在");
        MyAssert.notNull(target, "excel 映射实体类不存在");

        //反射获取注解 数据
        Excel excel = target.getAnnotation(Excel.class);
        if (null == excel) {
            throw new ExcelTypeException("实体类映射失败 excel注解不存在", "当前映射实体类没有");
        }

        //获取sheet表名
        String sheetName = excel.sheetName();
        if (StringUtils.isBlank(sheetName)) {
            sheetName = target.getSimpleName();
        }
        Sheet sheet = workbook.getSheet(sheetName);

        MyAssert.notNull(sheet, "sheet: " + sheetName + "不存在");
        return sheet;

    }

    @Override
    public IExcelColValHandler getIExcelColValHandler() {
        return IExcelColValHandler;
    }

    @Override
    public void setIExcelColValHandler(IExcelColValHandler excelColValHandler) {
        IExcelColValHandler = excelColValHandler;
    }

}
