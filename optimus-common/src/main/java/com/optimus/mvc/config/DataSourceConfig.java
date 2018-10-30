package com.optimus.mvc.config;

import com.optimus.db.DataSourceUtil;
import com.optimus.db.ReadWriteDetachTransactionManager;
import com.optimus.db.ReadWriteRoutingDataSource;
import com.optimus.utils.ConfigUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author caoawei
 * Created by on 2018/4/23.
 */
public class DataSourceConfig {

    private static final String DS_CONFIG_MASTER = "master";
    private static final String DS_CONFIG_PREFIX = "datasource";
    private static final String DS_CONFIG_CLASS = "driverClassName";
    private static final String DS_CONFIG_URL = "url";
    private static final String DS_CONFIG_USERNAME = "username";
    private static final String DS_CONFIG_PASSWORD = "password";

    @Bean(autowire = Autowire.BY_NAME,name = "transactionManager")
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager transactionManager = new ReadWriteDetachTransactionManager();
        return transactionManager;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {

        // 读写分离数据源
        ReadWriteRoutingDataSource dataSource = new ReadWriteRoutingDataSource();

        // 根据配置创建数据源
        Map<Object,Object> dses = obtainDataSourceConfig();

        // 数据源名称
        Set<Object> slaveDsNames = dses.keySet();

        // 设置主数据源
        dataSource.setDefaultTargetDataSource(dses.get(DS_CONFIG_MASTER));

        // 移除主数据源
        dses.remove(DS_CONFIG_MASTER);

        // 移除主数据源名称
        slaveDsNames.remove(DS_CONFIG_MASTER);

        // 设置从数据源(只读节点)
        dataSource.setTargetDataSources(dses);

        DataSourceUtil.setNodes(slaveDsNames.toArray(new String[slaveDsNames.size()]));
        return dataSource;
    }

    /**
     * 根据配置信息创建数据源
     * @return 数据源
     */
    private Map<Object,Object> obtainDataSourceConfig() {
        // 获取数据源配置(datasource开头的所有配置)
        Map<String,String> cfg = ConfigUtil.getConfigLike(DS_CONFIG_PREFIX);
        if(cfg.isEmpty()){
            return null;
        }

        Map<Object,Object> rs = new LinkedHashMap<>();
        for (Map.Entry<String,String> entry : cfg.entrySet()) {
            String key = entry.getKey().substring(DS_CONFIG_PREFIX.length()+1);
            // 第二个'.'的索引位置
            int index = key.indexOf(".");
            // 数据源名称
            String primaryKey = key.substring(0,index);
            BasicDataSource ds = (BasicDataSource) rs.get(primaryKey);
            if(ds == null){
                ds = new BasicDataSource();
                rs.put(primaryKey,ds);
            }

            // 设置数据源基本属性
            settingBasicCfg(key, ds, entry.getValue());
        }

        // 设置数据源通用属性
        settingDataSourceCommonCfg(rs);
        return rs;
    }

    /**
     * 数据源基本设置
     * @param key 数据源名称
     * @param ds 数据源对象
     * @param val 配置项值
     */
    private void settingBasicCfg(String key, BasicDataSource ds, String val) {
        if(key.contains(DS_CONFIG_CLASS)){
            ds.setDriverClassName(val);
        } else if(key.contains(DS_CONFIG_URL)){
            ds.setUrl(val);
        } else if(key.contains(DS_CONFIG_USERNAME)){
            ds.setUsername(val);
        } else if(key.contains(DS_CONFIG_PASSWORD)){
            ds.setPassword(val);
        }
    }

    /**
     * 数据源通用配置
     * @param rs 数据源
     */
    private void settingDataSourceCommonCfg(Map<Object, Object> rs) {
        for (Map.Entry<Object,Object> entry : rs.entrySet()){
            BasicDataSource bds = (BasicDataSource) entry.getValue();
            bds.setInitialSize(20);
            bds.setMaxActive(100);
            bds.setDefaultAutoCommit(false);
        }
    }


}
