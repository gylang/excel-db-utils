package com.gylang.excel.db.handler;

import com.gylang.excel.db.entity.SheetInfo;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

/**
 * @author gylang
 * data 2020/7/5
 * @version v0.0.1
 */
public interface IWorkBookCrudHandler<T> {


    /**
     * 查询一条
     *
     * @param sheet sheet表
     * @param t     映射对象查询
     * @return 查询结果
     */
    T selectOne(Sheet sheet, SheetInfo sheetInfo, T t);

    /**
     * 查询一条
     *
     * @param sheet sheet表
     * @param t     映射对象查询
     * @return 查询结果
     */
    List<T> selectList(Sheet sheet, SheetInfo sheetInfo, T t);

    /**
     * 查询一条
     *
     * @param sheet sheet表
     * @param t     映射对象查询
     * @return 删除结果
     */
    int delete(Sheet sheet, SheetInfo sheetInfo, T t);

    /**
     * @param sheet   sheet表
     * @param t       映射对象查询
     * @param example 更新查询条件
     * @return 返回更新结果
     */
    int updateByExample(Sheet sheet, SheetInfo sheetInfo, T example, T t);

    void insert(Sheet sheet, SheetInfo sheetInfo, Object t);

    /**
     * 获取excel数据处理实现类
     *
     * @return 数据处理实现类
     */
    IExcelColValHandler getIExcelColValHandler();

    /**
     * 设置数据处理类
     *
     * @param IExcelColValHandler 数据处理实现类
     */
    void setIExcelColValHandler(IExcelColValHandler IExcelColValHandler);
}
