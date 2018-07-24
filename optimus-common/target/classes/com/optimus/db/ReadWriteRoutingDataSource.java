package com.optimus.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源-可切换数据源-读写分离
 * @author caoawei
 * Created by on 2018/4/23.
 */
public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.currentDataSource();
    }

    public static class DataSourceHolder {
        private static ThreadLocal<String> dataSourceLocal = new ThreadLocal<>();

        public static void setDataSource(String dsName) {
            dataSourceLocal.set(dsName);
        }

        public static String currentDataSource() {
            return dataSourceLocal.get();
        }

        public static void remove(){
            dataSourceLocal.remove();
        }
    }
}
