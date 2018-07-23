package com.optimus.common.mybatis.interceptor;

import com.optimus.common.constant.DBType;
import com.optimus.common.mybatis.PageList;
import com.optimus.common.mybatis.PageParam;
import com.optimus.common.mybatis.PageUtil;
import com.optimus.common.mybatis.parser.SqlParser;
import com.optimus.utils.Utils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisPageInterceptor implements Interceptor {

    @Autowired
    private SqlParser sqlParser;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (PageUtil.needPage() && sqlParser.isSupport(DBType.MYSQL)) {
            Executor executor = (Executor) invocation.getTarget();
            MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
            Object parameterObject = invocation.getArgs()[1];
            BoundSql boundSql = ms.getBoundSql(parameterObject);
            RowBounds rowBounds = (RowBounds) invocation.getArgs()[2];
            ResultHandler resultHandler = (ResultHandler) invocation.getArgs()[3];
            Connection connection = executor.getTransaction().getConnection();

            String countSql = sqlParser.parseWithCountQuery(boundSql.getSql().trim());
            BoundSql countBoundSql = copyBoundSql(ms, boundSql, countSql);
            PreparedStatement ps = connection.prepareStatement(countSql);
            setParameters(ps, ms, countBoundSql);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                PageParam pageParam = resolvePageParam(resultSet);
                PageList pageList = initPageList(pageParam);

                String pageSql = sqlParser.parseWithPageQuery(boundSql.getSql().trim());
                Utils.setFieldValue("sql", boundSql, pageSql);

                List list = query(executor,ms, parameterObject, boundSql, rowBounds, resultHandler);
                pageList.addAll(list);
                return pageList;
            }
            resultSet.close();
            connection.close();
        }

        return invocation.proceed();
    }

    private PageParam resolvePageParam(ResultSet resultSet) throws SQLException {
        PageParam pageParam = PageUtil.getPageParam();
        int count = resultSet.getInt(1);
        int pageNumber = (count / pageParam.getLimit()) + 1;
        pageParam.setTotalCount(count);
        pageParam.setPageNumber(pageNumber);
        return pageParam;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private List query(Executor executor,MappedStatement ms, Object parameterObject, BoundSql boundSql, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException {
        CacheKey cacheKey = createCacheKey(executor, ms, parameterObject, rowBounds, boundSql);
        return executor.query(ms, parameterObject, rowBounds, resultHandler, cacheKey, boundSql);
    }

    private BoundSql copyBoundSql(MappedStatement ms, BoundSql boundSql, String countSql) {
        BoundSql countBoundSql = new BoundSql(ms.getConfiguration(), countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        Object additionalParameters = Utils.getFieldValue("additionalParameters", boundSql);
        Utils.setFieldValue("additionalParameters", countBoundSql, additionalParameters);
        Object metaParameters = Utils.getFieldValue("metaParameters", boundSql);
        Utils.setFieldValue("metaParameters", countBoundSql, metaParameters);
        return countBoundSql;
    }

    private PageList<?> initPageList(PageParam pageParam) {
        PageList<?> pageList = new PageList<>();
        pageList.setStart(pageParam.getStart());
        pageList.setLimit(pageParam.getLimit());
        pageList.setPageSize(pageParam.getPageSize());
        pageList.setPageNumber(pageParam.getPageNumber());
        pageList.setTotalCount(pageParam.getTotalCount());
        pageList.setPage(pageParam.getPage());
        return pageList;
    }

    private CacheKey createCacheKey(Executor executor, MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql) {
        return executor.createCacheKey(ms, parameterObject, rowBounds, boundSql);
    }

    private void setParameters(PreparedStatement ps, MappedStatement ms, BoundSql boundSql) {
        ErrorContext.instance().activity("setting parameters").object(ms.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = ms.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            Object parameterObject = boundSql.getParameterObject();
            for (int i = 0; i < parameterMappings.size(); ++i) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    String propertyName = parameterMapping.getProperty();
                    Object value;
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }

                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value == null && jdbcType == null) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }

                    try {
                        typeHandler.setParameter(ps, i + 1, value, jdbcType);
                    } catch (TypeException var10) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + var10, var10);
                    } catch (SQLException var11) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + var11, var11);
                    }
                }
            }
        }
    }

}
