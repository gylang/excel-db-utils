package com.gylang.excel.db.handler.impl;

import com.gylang.excel.db.entity.ColHeaderInfo;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.enums.ExcelColType;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.myassert.MyAssert;
import com.gylang.excel.db.util.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public class SimpleExcelColHandlerImpl implements IExcelColValHandler {

    private static final Map<String, ExcelColType> EXCEL_TYPE = new HashMap<>(6);

    static {
        EXCEL_TYPE.put(String.class.getName(), ExcelColType.STRING);
        EXCEL_TYPE.put(Integer.class.getName(), ExcelColType.INTEGER);
        EXCEL_TYPE.put(Float.class.getName(), ExcelColType.FLOAT);
        EXCEL_TYPE.put(Double.class.getName(), ExcelColType.DOUBLE);
        EXCEL_TYPE.put(Date.class.getName(), ExcelColType.TIME);
        EXCEL_TYPE.put(BigDecimal.class.getName(), ExcelColType.BIG_DECIMAL);
    }


    @Override
    public void setData(Row row, Object target, SheetInfo sheetInfo) {

        MyAssert.notNull(sheetInfo, "实体类映射关系不存在");
        MyAssert.notNull(target, "赋值对象不能为空");

        //获取实体类映射关系
        Map<String, ColHeaderInfo> sheetInfoTarget = sheetInfo.getTarget();

        if (null == sheetInfoTarget || sheetInfoTarget.isEmpty()) {

            MyAssert.notNull(sheetInfo, "实体类映射关系不存在");

        } else {

            //通过反射setter赋值
            Field[] fields = target.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                ColHeaderInfo colHeaderInfo = sheetInfoTarget.get(name);
                //通过映射和获取所属单元格数据
                Cell cell = row.getCell(colHeaderInfo.getIndex());
                //转成 映射类型
                Object val = getDataForExcelColType(colHeaderInfo, cell);
                //反射赋值
                PropertyUtils.setValue(target, name, val);
            }

        }
    }



    @Override
    public String toStr(Cell cell) {

        return cell.getStringCellValue();
    }

    @Override
    public Date toDate(Cell cell, String format) {
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            return cell.getDateCellValue();
        } else if (cell.getCellType().equals(CellType.STRING)) {

            //todo 待处理
            String dateCellValue = cell.getStringCellValue();
            return new Date(dateCellValue);
        } else {
            return null;
        }
    }

    @Override
    public Integer toInteger(Cell cell) {
        if (cell.getCellType().equals(CellType.STRING)) {
            String value = cell.getStringCellValue();
            return Integer.valueOf(value);
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
            double value = cell.getNumericCellValue();
            return (int) value;
        } else {
            return null;
        }
    }

    @Override
    public Float toFloat(Cell cell) {
        if (cell.getCellType().equals(CellType.STRING)) {
            String value = cell.getStringCellValue();
            return Float.valueOf(value);
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
            double value = cell.getNumericCellValue();
            return (float) value;
        } else {
            return null;
        }
    }

    @Override
    public Double toDouble(Cell cell) {
        if (cell.getCellType().equals(CellType.STRING)) {
            String value = cell.getStringCellValue();
            return Double.valueOf(value);
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
            return cell.getNumericCellValue();
        } else {
            return null;
        }

    }


    @Override
    public BigDecimal toBigDecimal(Cell cell) {
        if (cell.getCellType().equals(CellType.STRING)) {
            String value = cell.getStringCellValue();
            return new BigDecimal(value);
        } else if (cell.getCellType().equals(CellType.NUMERIC)) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        } else {
            return null;
        }

    }


    @Override
    public Object getDataForExcelColType(ColHeaderInfo excelColType, Cell cell) {

        if (String.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            return toStr(cell);

        } else if (Integer.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {

            return toInteger(cell);
        } else if (Float.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            return toStr(cell);

        } else if (Double.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            return toDouble(cell);

        } else if (Date.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            return toDate(cell, "yyyyMMdd hh:mm:ss");

        } else if (BigDecimal.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            return toBigDecimal(cell);

        } else if (ExcelColType.ONLY_TIME.name().equalsIgnoreCase(excelColType.getTypeName())) {
            return toDate(cell, "hh:mm:ss");

        } else if (ExcelColType.DATE.name().equalsIgnoreCase(excelColType.getTypeName())) {
            return toDate(cell, "yyyyMMdd");
        }

        return null;
    }


}
