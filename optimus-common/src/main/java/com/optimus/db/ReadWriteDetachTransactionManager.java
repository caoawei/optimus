package com.optimus.db;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * @author caoawei
 * Created by on 2018/4/23.
 */
public class ReadWriteDetachTransactionManager extends DataSourceTransactionManager {

    /**
     * 从节点名称,务必保证此处的名称与{@link ReadWriteRoutingDataSource#targetDataSources} 中设置的名称一致.
     */
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        // 根据 @Transactional 注解设置应当使用的数据源.当 readOnly=true时使用只读数据源
        DataSourceUtil.settingReadOnlyDataSourceIfNecessary(definition);
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        try{
            super.doCleanupAfterCompletion(transaction);
        } finally {
            // threadLocal 数据清除
            DataSourceUtil.clearDataSourceLocal();
        }
    }
}
