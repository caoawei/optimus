package com.optimus.rpc.config;

/**
 * Created by Administrator on 2018/5/21.
 */
public class Config {
    private String applicationName;
    private String group;
    private String version;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
