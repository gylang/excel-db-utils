package com.gylang.excel.db.util;


import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public class CellTypeUtils {

    public static void setCell(Cell cell, Object object) {

        if (object.getClass() == String.class) {

            cell.setCellValue((String) object);
        } else if (object.getClass() == Integer.class) {

            cell.setCellValue((Integer) object);
        } else if (object.getClass() == Date.class) {

            cell.setCellValue((Date) object);
        } else if (object.getClass() == Float.class) {
            cell.setCellValue((Float) object);
        } else if (object.getClass() == Double.class) {

            cell.setCellValue((Double) object);
        } else if (object.getClass() == BigDecimal.class) {

            cell.setCellValue(((BigDecimal) object).doubleValue());
        }
    }
}
