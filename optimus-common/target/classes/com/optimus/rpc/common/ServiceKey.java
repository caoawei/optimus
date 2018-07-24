package com.optimus.rpc.common;

import com.optimus.utils.Utils;

/**
 * 服务标识:
 * 对于服务消费者来说,区分不同的服务根据: group+interfaceName+version,
 * 此时的不同服务指的是 服务的功能不同
 *
 * 而在进行本地服务注册表更新时,不同服务的区别为: host+port+group+interfaceName+version
 * 此时的不同服务的范围更广泛,相同服务的多个提供者也是视为不同的服务.因为在调用或更新阶段,只会调用一个提供者.
 * @author caoawei
 * Created on 2018/5/21.
 */
public class ServiceKey {
    private String interfaceName;
    private String group;
    private String version;
    public static ServiceKey create(String interfaceName,String group,String version) {
        if(interfaceName == null) {
            throw new NullPointerException("interfaceClass is null");
        }
        ServiceKey sk = new ServiceKey();
        sk.setInterfaceName(interfaceName);
        sk.setGroup(group);
        sk.setVersion(version);
        if(Utils.isEmpty(sk.group)) {
            sk.setGroup("rpc");
        }
        if(Utils.isEmpty(sk.version)) {
            sk.setVersion("1.0");
        }
        return sk;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        ServiceKey that = (ServiceKey) object;

        if (interfaceName != null ? !interfaceName.equals(that.interfaceName) : that.interfaceName != null)
            return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;
    }

    @Override
    public int hashCode() {
        int result = interfaceName != null ? interfaceName.hashCode() : 0;
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
