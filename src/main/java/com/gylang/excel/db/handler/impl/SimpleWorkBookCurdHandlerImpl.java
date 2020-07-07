package com.gylang.excel.db.handler.impl;

import com.gylang.excel.db.entity.ColHeaderInfo;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.handler.IExcelColValHandler;
import com.gylang.excel.db.handler.IWorkBookCurdHandler;
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
public class SimpleWorkBookCurdHandlerImpl implements IWorkBookCurdHandler<Object> {

    private IExcelColValHandler excelColValHandler;

    @Override
    public Object selectOne(Sheet sheet, SheetInfo sheetInfo, Object t) {
        //获取查询映射条件
        Map<String, Object> query = PropertyUtils.toMap(t);
        // 字段映射关系
        Map<String, ColHeaderInfo> fieldMap = sheetInfo.getTarget();
        //从第二行开始
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            //判断是否为目标列
            if (isTargetRow(sheet, i, query, fieldMap)) {

                try {
                    //给对象赋值
                    Object target = t.getClass().newInstance();
                    excelColValHandler.setData(sheet.getRow(i), target, sheetInfo);
                    return target;
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return null;

    }

    @Override
    public List<Object> selectList(Sheet sheet, SheetInfo sheetInfo, Object t) {

        //获取查询映射条件
        Map<String, Object> query = PropertyUtils.toMap(t);
        // 字段映射关系
        Map<String, ColHeaderInfo> fieldMap = sheetInfo.getTarget();
        //查询目标返回值
        List<Object> targetList = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            if (isTargetRow(sheet, i, query, fieldMap)) {
                try {
                    Object target = t.getClass().newInstance();
                    excelColValHandler.setData(sheet.getRow(i), target, sheetInfo);
                    targetList.add(target);
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }

        return targetList;
    }

    @Override
    public int delete(Sheet sheet, SheetInfo sheetInfo, Object t) {

        //获取查询映射条件
        Map<String, Object> query = PropertyUtils.toMap(t);
        // 字段映射关系
        Map<String, ColHeaderInfo> fieldMap = sheetInfo.getTarget();
        //查询目标列
        List<Row> delIndex = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            if (isTargetRow(sheet, i, query, fieldMap)) {
                delIndex.add(sheet.getRow(i));
            }

        }
        delIndex.forEach(sheet::removeRow);

        return delIndex.size();
    }

    @Override
    public int updateByExample(Sheet sheet, SheetInfo sheetInfo, Object example, Object t) {

        //获取查询映射条件
        Map<String, Object> query = PropertyUtils.toMap(t);
        // 字段映射关系
        Map<String, ColHeaderInfo> fieldMap = sheetInfo.getTarget();
        int updateLine = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            if (isTargetRow(sheet, i, query, fieldMap)) {
                updateLine++;
                Map<String, Object> updateData = PropertyUtils.toMap(t);

                for (Map.Entry<String, Object> entry : updateData.entrySet()) {
                    //获取实体类与excel映射关系
                    ColHeaderInfo colHeaderInfo = fieldMap.get(entry.getKey());
                    //获取目标单元格
                    Cell cell = sheet.getRow(i).getCell(colHeaderInfo.getIndex());
                    //给单元给重新赋值
                    CellTypeUtils.setCell(cell, entry.getValue());
                }

            }
        }

        return updateLine;
    }

    @Override
    public void insert(Sheet sheet, SheetInfo sheetInfo, Object t) {

        //插入数据
        Map<String, Object> insertData = PropertyUtils.toMap(t);
        // 字段映射关系
        Map<String, ColHeaderInfo> fieldMap = sheetInfo.getTarget();
        //创建一行
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        for (Map.Entry<String, Object> entry : insertData.entrySet()) {
            //获取实体类与excel映射关系
            ColHeaderInfo colHeaderInfo = fieldMap.get(entry.getKey());
            if (null != colHeaderInfo) {
                //创建单元给
                Cell cell = row.createCell(colHeaderInfo.getIndex(), CellType.STRING);
                //给单元格赋值
                CellTypeUtils.setCell(cell, entry.getValue());
            }

        }
    }

    /**
     *  获取目标列
     * @param sheet sheet表
     * @param index 当前下表
     * @param query 查询条件
     * @param fieldMap 实体类映射关系
     * @return 是否为目标列
     */
    private boolean isTargetRow(Sheet sheet, int index, Map<String, Object> query, Map<String, ColHeaderInfo> fieldMap) {

        Row row = sheet.getRow(index);
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            //获取映射关系
            ColHeaderInfo colHeaderInfo = fieldMap.get(entry.getKey());
            //获取单元格值
            Object cellVal = excelColValHandler.getDataForExcelColType(colHeaderInfo, row.getCell(colHeaderInfo.getIndex()));
            //值判断
            if (!entry.getValue().equals(cellVal)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public IExcelColValHandler getExcelColValHandler() {
        return excelColValHandler;
    }

    @Override
    public void setExcelColValHandler(IExcelColValHandler excelColValHandler) {
        this.excelColValHandler = excelColValHandler;
    }


}
