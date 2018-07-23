package com.optimus.common.constant;

/**
 * 数据库类型
 * @author caoawei
 */
public enum DBType {

    MYSQL("mysql"),
    ;

    private String key;

    DBType(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static DBType instance(String key){
        for(DBType dbType : DBType.values()){
            if(dbType.getKey().trim().equals(key.trim())){
                return dbType;
            }
        }

        return null;
    }
}
