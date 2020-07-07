package com.gylang.excel.db.dbproxy;

import com.gylang.excel.db.context.IWorkBookContext;
import com.gylang.excel.db.entity.SheetInfo;
import com.gylang.excel.db.handler.IWorkBookCurdHandler;

import java.util.List;

/**
 *  代理mapper 实现类
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public class ExcelMapperImpl implements ExcelMapper<Object> {
    /**
     * 实体类映射sheet关系
     */
    private final SheetInfo sheetinfo;
    /**
     *
     */
    private static IWorkBookCurdHandler<Object> WORK_BOOK_CRUD_HANDLER = null;
    private static IWorkBookContext WORK_BOOK_CONTEXT = null;

    /**
     * 初始化excelMapper
     * @param sheetInfo excel与实体类映射关系
     * @param workCurlHandler 基本curd操作实现类
     * @param workBookContext 存储workbook的容器环境
     * @return excelMapper
     */
    public static ExcelMapperImpl init(SheetInfo sheetInfo, IWorkBookCurdHandler<Object> workCurlHandler, IWorkBookContext workBookContext) {
        WORK_BOOK_CRUD_HANDLER = workCurlHandler;
        WORK_BOOK_CONTEXT = workBookContext;
        return new ExcelMapperImpl(sheetInfo);
    }

    private ExcelMapperImpl(SheetInfo sheetInfo) {
        this.sheetinfo = sheetInfo;
    }

    @Override
    public Object selectOne(Object o) {

        return WORK_BOOK_CRUD_HANDLER.selectOne(WORK_BOOK_CONTEXT.getSheet(sheetinfo), sheetinfo, o);
    }

    @Override
    public List<Object> selectList(Object o) {

        return WORK_BOOK_CRUD_HANDLER.selectList(WORK_BOOK_CONTEXT.getSheet(sheetinfo), sheetinfo, o);

    }

    @Override
    public int delete(Object o) {

        return WORK_BOOK_CRUD_HANDLER.delete(WORK_BOOK_CONTEXT.getSheet(sheetinfo), sheetinfo, o);

    }

    @Override
    public int updateByExample(Object example, Object o) {

        return WORK_BOOK_CRUD_HANDLER.updateByExample(WORK_BOOK_CONTEXT.getSheet(sheetinfo), sheetinfo, example, o);

    }

    @Override
    public void insert(Object o) {

        WORK_BOOK_CRUD_HANDLER.insert(WORK_BOOK_CONTEXT.getSheet(sheetinfo), sheetinfo, o);

    }
}
