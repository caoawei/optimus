package com.optimus.db;

import org.springframework.transaction.TransactionDefinition;

/**
 * @author caoawei
 * Created by on 2018/4/23.
 */
public class DataSourceUtil {

    private static String[] nodes;

    public static void setNodes(String[] nodes){
        DataSourceUtil.nodes = nodes;
    }

    public static String[] getNodes(){
        return DataSourceUtil.nodes;
    }

    public static void settingReadOnlyDataSourceIfNecessary(TransactionDefinition definition){
        if(definition != null && definition.isReadOnly()){
            if(nodes != null && nodes.length > 0) {
                String dsName;
                if(nodes.length == 1){
                    dsName = nodes[0];
                } else {
                    dsName = nodes[(int) (Math.random() * nodes.length)];
                }
                ReadWriteRoutingDataSource.DataSourceHolder.setDataSource(dsName);
            }
        }
    }

    public static void clearDataSourceLocal(){
        ReadWriteRoutingDataSource.DataSourceHolder.remove();
    }
}
