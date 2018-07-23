package com.optimus.common.mybatis.support;

import com.optimus.common.mybatis.parser.SqlParser;
import org.apache.commons.lang.text.StrBuilder;

/**
 * 提供默认实现 解析成count查询的功能
 *
 * 新加的数据库类型 继承 {@link BaseSqlParser} 或 实现 {@link SqlParser}接口
 * @author caoawei
 */
public abstract class BaseSqlParser implements SqlParser {

    private static final String COUNT_SQL_PREFIX = "SELECT COUNT(*) ";

    @Override
    public String parseWithCountQuery(String srcSql) {
        int index = srcSql.indexOf("from");
        StrBuilder newSql = new StrBuilder(COUNT_SQL_PREFIX);
        newSql.append(srcSql.substring(index));
        return newSql.toString();
    }
}
