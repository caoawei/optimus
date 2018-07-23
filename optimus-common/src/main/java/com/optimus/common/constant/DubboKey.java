package com.optimus.common.constant;

/**
 * Created by Administrator on 2017/11/28.
 */
public enum  DubboKey {
    Page_List("page_list"),
    ;

    private String key;

    DubboKey(String key){
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
