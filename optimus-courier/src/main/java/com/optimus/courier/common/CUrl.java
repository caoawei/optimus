package com.optimus.courier.common;

import com.optimus.exception.CourierException;

/**
 * Created by caoawei on 2018/7/5.
 */
public class CUrl {

    // 协议名
    private String protocol;
    // ip
    private String host;
    // 端口号
    private int port;
    // 接口名
    private String interfaceName;
    // 分组
    private String group;
    // 版本
    private String version;

    private void checkInstance() {
        if (protocol == null || protocol.isEmpty()) {
            throw new CourierException("The class name is "+CUrl.class.getName()+",协议为空");
        }
        if (host == null || host.isEmpty()) {
            throw new CourierException("The class name is "+CUrl.class.getName()+",host为空");
        }
        if (port < 0 || port > 65535) {
            throw new CourierException("The class name is "+CUrl.class.getName()+",端口号不合法");
        }
        if (interfaceName == null || interfaceName.isEmpty()) {
            throw new CourierException("The class name is "+CUrl.class.getName()+",接口名为空");
        }
    }

    @Override
    public String toString() {
        checkInstance();
        StringBuilder rs = new StringBuilder(this.protocol);
        rs.append(":").append("//");
        rs.append(this.host);
        rs.append(":");
        rs.append(this.port);
        rs.append("?");
        rs.append("interfaceName=");
        rs.append(this.interfaceName).append("&");
        rs.append("group=");
        rs.append(this.group).append("&");
        rs.append("version=");
        rs.append(this.version);
        return rs.toString();
    }
}
