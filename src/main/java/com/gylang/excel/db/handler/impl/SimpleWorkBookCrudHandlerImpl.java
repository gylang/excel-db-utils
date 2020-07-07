package com.gylang.excel.db.handler.impl;

import com.gylang.excel.db.entity.ColHeaderInfo;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.handler.IWorkBookCrudHandler;
import com.gylang.excel.db.util.CellTypeUtils;
import com.gylang.excel.db.util.PropertyUtils;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gylang
 * data 2020/7/5
 * @version v0.0.1
 */
public class SimpleWorkBookCrudHandlerImpl implements IWorkBookCrudHandler<Object> {

    private IExcelColValHandler IExcelColValHandler;

    @Override
    public Object selectOne(Sheet sheet, SheetInfo sheetInfo, Object t) {

        Map<String, Object> query = PropertyUtils.toMap(t);
        Map<String, ColHeaderInfo> target = sheetInfo.getTarget();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            boolean isTarget = true;
            for (Map.Entry<String, Object> entry : query.entrySet()) {

                ColHeaderInfo colHeaderInfo = target.get(entry.getKey());

                Object cellVal = IExcelColValHandler.getDataForExcelColType(colHeaderInfo, row.getCell(colHeaderInfo.getIndex()));
                if (!entry.getValue().equals(cellVal)) {
                    isTarget = false;
                }
            }
            if (isTarget) {
                try {
                    Object targeT = t.getClass().newInstance();
                    IExcelColValHandler.setData(row, targeT, sheetInfo);
                    return targeT;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return null;

    }

    @Override
    public List<Object> selectList(Sheet sheet, SheetInfo sheetInfo, Object t) {

        Map<String, Object> query = PropertyUtils.toMap(t);
        Map<String, ColHeaderInfo> target = sheetInfo.getTarget();
        List<Object> targetList = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            boolean isTarget = true;
            for (Map.Entry<String, Object> entry : query.entrySet()) {

                ColHeaderInfo colHeaderInfo = target.get(entry.getKey());

                Object cellVal = IExcelColValHandler.getDataForExcelColType(colHeaderInfo, row.getCell(colHeaderInfo.getIndex()));
                if (!entry.getValue().equals(cellVal)) {
                    isTarget = false;
                }
            }
            if (isTarget) {
                try {
                    Object targeT =  t.getClass().newInstance();
                    IExcelColValHandler.setData(row, targeT, sheetInfo);
                    targetList.add(targeT);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return targetList;
    }

    @Override
    public int delete(Sheet sheet, SheetInfo sheetInfo, Object t) {

        Map<String, Object> query = PropertyUtils.toMap(t);
        Map<String, ColHeaderInfo> target = sheetInfo.getTarget();
        List<Row> delIndex = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            boolean isTarget = true;
            for (Map.Entry<String, Object> entry : query.entrySet()) {

                ColHeaderInfo colHeaderInfo = target.get(entry.getKey());

                Object cellVal = IExcelColValHandler.getDataForExcelColType(colHeaderInfo, row.getCell(colHeaderInfo.getIndex()));
                if (!entry.getValue().equals(cellVal)) {
                    isTarget = false;
                }
            }
            if (isTarget) {
                delIndex.add(row);
            }

        }
        delIndex.forEach(sheet::removeRow);

        return delIndex.size();
    }

    @Override
    public int updateByExample(Sheet sheet, SheetInfo sheetInfo, Object example, Object t) {

        Map<String, Object> query = PropertyUtils.toMap(example);
        Map<String, ColHeaderInfo> target = sheetInfo.getTarget();
        int updateLine = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            boolean isTarget = true;
            for (Map.Entry<String, Object> entry : query.entrySet()) {

                ColHeaderInfo colHeaderInfo = target.get(entry.getKey());

                Object cellVal = IExcelColValHandler.getDataForExcelColType(colHeaderInfo, row.getCell(colHeaderInfo.getIndex()));
                if (!entry.getValue().equals(cellVal)) {
                    isTarget = false;
                }
            }
            if (isTarget) {
                updateLine++;
                Map<String, Object> updateData = PropertyUtils.toMap(t);
                for (Map.Entry<String, Object> entry : updateData.entrySet()) {

                    ColHeaderInfo colHeaderInfo = target.get(entry.getKey());
                    Cell cell = row.getCell(colHeaderInfo.getIndex());
                    CellTypeUtils.setCell(cell, entry.getValue());
                }

            }
        }

        return updateLine;
    }

    @Override
    public void insert(Sheet sheet, SheetInfo sheetInfo, Object t) {

        Map<String, Object> insertData = PropertyUtils.toMap(t);
        Map<String, ColHeaderInfo> target = sheetInfo.getTarget();
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        for (Map.Entry<String, Object> entry : insertData.entrySet()) {

            ColHeaderInfo colHeaderInfo = target.get(entry.getKey());
            if (null != colHeaderInfo) {
                Cell cell = row.createCell(colHeaderInfo.getIndex(), CellType.STRING);
                CellTypeUtils.setCell(cell, entry.getValue());
            }

        }
    }

    @Override
    public IExcelColValHandler getIExcelColValHandler() {
        return IExcelColValHandler;
    }

    @Override
    public void setIExcelColValHandler(IExcelColValHandler IExcelColValHandler) {
        this.IExcelColValHandler = IExcelColValHandler;
    }


}
