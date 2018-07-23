package com.optimus.mq.kafka;

import com.optimus.common.exception.BizException;
import com.optimus.utils.ConfigUtil;
import com.optimus.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class KafkaUtil {

    private static final Logger logger = LoggerFactory.getLogger(KafkaUtil.class);

    /**
     * 根据配置的kafka-broker 进行连通性检测
     * @return 如果所有的broker均可连通则返回true,否则则返回false
     */
    public static boolean ping(){
        String servers = ConfigUtil.getConfig("mq.kafka.bootstrap.servers");
        if(Utils.isEmpty(servers)){
            logger.info("[kafka-当前系统未配置kafka-broker]");
            return false;
        }

        String[] nodes = servers.split(",");
        for (String nodeCfg : nodes){
            String[] hostAndPort = checkNodeCfg(nodeCfg);
            Socket socket = null;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(hostAndPort[0],Integer.valueOf(hostAndPort[1].trim())), 3000);
            } catch (Exception e) {
                if(e instanceof ConnectException){
                    logger.error("[kafka-broker连接失败: host:{},port:{},请检查是否启动kafka-broker]",hostAndPort[0],hostAndPort[1],e);
                }
                return false;
            } finally {
                if(socket != null){
                    try {
                        socket.close();
                    } catch (Exception e){
                        logger.error("[kafka-ping操作-流关闭失败]",e);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 验证kafka-broker 配置是否正确
     * @param nodeCfg host:port类型的字符串
     * @return 如果验证通过,则返回 {"ip","port"}形式的字符串数组
     */
    private static String[] checkNodeCfg(String nodeCfg){
        String[] hostAndPort = nodeCfg.split(":");
        if(hostAndPort == null || hostAndPort.length != 2){
            throw new BizException("[kafka-broker节点配置失败,应按照 host1:port2,host2:port2 格式进行配置]");
        }
        if(Integer.valueOf(hostAndPort[1].trim()) <= 0){
            throw new IllegalArgumentException("port must be greater than 0");
        }
        return hostAndPort;
    }
}
