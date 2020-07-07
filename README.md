# excel-db-utils
基于poi封装的excel当成数据库的来操作，基于实体类映射 mapper方式简单操作excel实现增删查改
# excel-db-utils

将excel表当成使用数据库一样操作，通过实体类映射当做查询条件来进行查询，当前只有如下功能

```
 /**
     * 查询一条
     *
     * @param t 映射对象查询条件
     * @return 查询结果
     */
    T selectOne(T t);

    /**
     * 查询列表
     *
     * @param t 映射对象查询条件
     * @return 查询结果
     */
    List<T> selectList(T t);

    /**
     * 删除
     *
     * @param t 映射对象查询条件
     * @return 删除结果
     */
    int delete(T t);

    /**
     * 按条件更新
     *
     * @param t       映射对象查询条件
     * @param example 更新查询条件
     * @return 更新影响数量
     */
    int updateByExample(T example, T t);

    /**
     * 插入
     *
     * @param t 插入对象数据
     */
    void insert(T t);
```



## 使用方式

构建 excel数据处理对象

```java
IExcelColValHandler IExcelColValHandler = new SimpleExcelColHandlerImpl();
```

构建 增删查改处理对象

```
IWorkBookCurdHandler<Object> workBookCrudHandler = new SimpleWorkBookCurdHandlerImpl();
```

构建excel处理对象

```
IExcelHandler simpleExcelHandler = new SimpleExcelHandler();
```

构建javabean映射excel相关处理

```
IAnnotationExcelTypeHandler simpleIAnnotationExcelTypeHandler = new SimpleAnnotationExcelTypeHandlerImpl();
```

缓存相关 workbook对象统一管理

```
IWorkBookContext simpleWorkBookContext = new SimpleWorkBookContext();
```

代理mapper (通过实体类创建mapper) 

```
List<Class<?> > classList = new ArrayList<>();
classList.add(Test.class);
```

构建代理对象

```java
ExcelMapperProxyFactory excelMapperProxy = ExcelMapperProxyFactory.builder()
                .annotationExcelTypeHandler(simpleIAnnotationExcelTypeHandler)
                .excelHandler(simpleExcelHandler)
                .excelColValHandler(simpleExcelColHandler)
                .workBookContext(simpleWorkBookContext)
                .workBookCrudHandler(workBookCrudHandler)
                .classList(classList)
                .build()
                .init(classList);
```

获取excelMapper

```java
ExcelMapper<Test> testExcelMapper = excelMapperProxy.getProxy(Test.class);
```

简单查询

```
testExcelMapper.selectList(Test.builder().build())
```

