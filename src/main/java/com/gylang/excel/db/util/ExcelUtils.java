package com.gylang.excel.db.util;


import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author gylang,
 * date 2020/4/26,
 * @version 1.0
 */
public class ExcelUtils {


    /**
     * 解析excel成workbook
     *
     * @param excelPath excel路径
     * @return workbook
     * @throws IOException io读取excel异常
     */
    public static Workbook getExcel(String excelPath) throws IOException {

        // 1、构造excel文件输入流对象
        InputStream is = new FileInputStream(excelPath);
        // 2、声明工作簿对象
        return WorkbookFactory.create(is);
    }

    /**
     * 解析excel成workbook
     *
     * @param excelPath excel路径
     * @return workbook
     * @throws IOException io读取excel异常
     */
    public static Workbook getExcelOrBuild(String excelPath) throws IOException {

        // 1、构造excel文件输入流对象
        InputStream is = null;
        try {
            is = new FileInputStream(excelPath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        if (null == is) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            workbook.getCreationHelper();


            return workbook;
        }
        // 2、声明工作簿对象
        return WorkbookFactory.create(is);
    }


    /**
     * 获取头部标题
     *
     * @param workbook excel对象
     * @param sheet    sheet表
     * @return 返回头部标题
     */
    public static List<String> getHeaderForExcel(Workbook workbook, int sheet) {


        if (null != workbook && workbook.getNumberOfSheets() > sheet) {

            Sheet sheetAt = workbook.getSheetAt(sheet);
            //存在标题
            if (0 < sheetAt.getLastRowNum()) {

                List<String> header = new ArrayList<>();
                Iterator<Cell> cellIterator = sheetAt.getRow(0).cellIterator();
                while (cellIterator.hasNext()) {
                    header.add(cellIterator.next().getStringCellValue());
                }
                return header;

            }

        }
        return new ArrayList<>();
    }


    /**
     * 添加一行数据
     *
     * @param t        添加的数据
     * @param workbook workbook对象
     */
    public static  void addRow(Object t, Workbook workbook, List<String> colName,
                                  Map<String, Object> data, int sheet) {
        int sheets = workbook.getNumberOfSheets();
        //当前excel为空 创建标题栏
        if (sheet < sheets) {
            Sheet sheetAt = workbook.getSheetAt(sheet);
            int newRowNum = sheetAt.getLastRowNum() + 1;
            Row row = sheetAt.createRow(newRowNum);

            for (int i = 0; i < colName.size(); i++) {
                if (data.containsKey(colName.get(i))) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(String.valueOf(data.get(colName.get(i))));
                }
            }
        } else {
            Sheet createSheet = workbook.createSheet();
            Row row = createSheet.createRow(0);
            for (int i = 0; i < colName.size(); i++) {
                row.createCell(i).setCellValue(colName.get(i));
            }
            //回调添加数值
            addRow(t, workbook, colName, data, sheet);
        }

    }



    /**
     * 从excel中获取列名
     *
     * @param workbook   workbook对象
     * @param sheetIndex 获取的sheet值
     * @return 列名
     */
    public static List<String> getColNameForExcel(Workbook workbook, int sheetIndex) {

        ArrayList<String> colName = Lists.newArrayList();
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = sheet.getLastRowNum();
        if (0 < lastRowNum) {
            Row row = sheet.getRow(sheet.getFirstRowNum());
            for (Cell cell : row) {
                colName.add(cell.getStringCellValue());
            }
        }
        return colName;
    }


    public static void write(Workbook workbook, String excelPath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(excelPath);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
