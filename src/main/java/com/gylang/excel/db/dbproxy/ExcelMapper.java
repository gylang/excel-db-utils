package com.gylang.excel.db.dbproxy;



import java.util.List;

/**
 * 代理接口
 *
 * @author gylang
 * data 2020/7/6
 * @version v0.0.1
 */
public interface ExcelMapper<T> {

    /**
     * 查询一条
     *
     * @param t 映射对象查询
     * @return 查询结果
     */
    T selectOne(T t);

    /**
     * 查询列表
     *
     * @param t 映射对象查询
     * @return 查询结果
     */
    List<T> selectList(T t);

    /**
     * 删除
     *
     * @param t 映射对象查询
     * @return 删除结果
     */
    int delete(T t);

    /**
     * 按条件更新
     *
     * @param t       映射对象查询
     * @param example 更新查询条件
     * @return 更新影响数量
     */
    int updateByExample(T example, T t);

    /**
     * 插入
     *
     * @param t 映射对象查询
     */
    void insert(T t);


}