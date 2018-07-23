package com.optimus.rpc.common.tools;

import com.optimus.common.exception.BizException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by Administrator on 2018/5/23.
 */
public class IPUtil {

    public static InetAddress localIp() {
        try {
            InetAddress rs = null;
            Enumeration<NetworkInterface> networkInterfaceEnumeration =  NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    if(inetAddress.isSiteLocalAddress()) {
                        rs = inetAddress;
                        return rs;
                    } else if(rs == null) {
                        rs = inetAddress;
                    }
                }
            }

            if(rs == null) {
                rs = InetAddress.getLocalHost();
            }

            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e);
        }
    }

}
