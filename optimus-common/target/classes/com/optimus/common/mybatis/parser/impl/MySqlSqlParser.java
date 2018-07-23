package com.optimus.common.mybatis.parser.impl;

import com.optimus.common.constant.DBType;
import com.optimus.common.mybatis.PageParam;
import com.optimus.common.mybatis.ThreadPageUtil;
import com.optimus.common.mybatis.support.BaseSqlParser;
import org.springframework.stereotype.Component;

/**
 * mysql 数据库 sql解析实现类
 * @author caoawei
 */

@Component
public class MySqlSqlParser extends BaseSqlParser {
    @Override
    public String parseWithPageQuery(String srcSql) {
        PageParam pageParam = ThreadPageUtil.getPageParam();
        StringBuilder newSql = new StringBuilder(srcSql);
        return newSql.append(" limit ").append(pageParam.getStart()).append(",").append(pageParam.getLimit()).toString();
    }

    @Override
    public boolean isSupport(DBType dbType) {
        return DBType.MYSQL == dbType;
    }
}
