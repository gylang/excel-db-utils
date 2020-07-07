package com.gylang.excel.db.handler.impl;

import com.gylang.excel.db.entity.ColHeaderInfo;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.myassert.MyAssert;
import com.gylang.excel.db.util.PropertyUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/4
 * @version v0.0.1
 */
public class SimpleExcelColHandlerImpl implements IExcelColValHandler {


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
    public Date toDate(Cell cell) {
        return cell.getDateCellValue();
    }

    @Override
    public Integer toInteger(Cell cell) {
        String value = cell.getStringCellValue();
        return Double.valueOf(value).intValue();
    }

    @Override
    public Float toFloat(Cell cell) {

        String value = cell.getStringCellValue();
        return Float.valueOf(value);
    }

    @Override
    public Double toDouble(Cell cell) {

        String value = cell.getStringCellValue();
        return Double.valueOf(value);

    }


    @Override
    public BigDecimal toBigDecimal(Cell cell) {

        String value = cell.getStringCellValue();
        return new BigDecimal(value);

    }


    @Override
    public Object getDataForExcelColType(ColHeaderInfo excelColType, Cell cell) {

        //除了日期 其他格式都用String处理
        if (String.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            cell.setCellType(CellType.STRING);
            return toStr(cell);
        } else if (Integer.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            cell.setCellType(CellType.STRING);
            return toInteger(cell);
        } else if (Float.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            cell.setCellType(CellType.STRING);
            return toStr(cell);

        } else if (Double.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            cell.setCellType(CellType.STRING);
            return toDouble(cell);

        } else if (Date.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            return toDate(cell);

        } else if (BigDecimal.class.getName().equalsIgnoreCase(excelColType.getTypeName())) {
            cell.setCellType(CellType.STRING);
            return toBigDecimal(cell);

        }
        return null;
    }


}
