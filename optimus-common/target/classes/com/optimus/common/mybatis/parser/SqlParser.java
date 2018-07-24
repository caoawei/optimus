package com.optimus.common.mybatis.parser;

import com.optimus.common.constant.DBType;

/**
 * SQL解析接口:提供count和分页查询两种解析方式
 * @author caoawei
 */
public interface SqlParser {

    /**
     * 解析原始sql 为 count 查询
     * @param srcSql
     * @return
     */
    String parseWithCountQuery(String srcSql);

    /**
     * 解析原始sql 为分页查询
     * @param srcSql
     * @return
     */
    String parseWithPageQuery(String srcSql);

    /**
     * 是否支持 指定的数据库类型
     * @param dbType 数据库类型
     * @return
     */
    boolean isSupport(DBType dbType);
}
