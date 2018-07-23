package com.optimus.utils;

import com.optimus.common.exception.BizException;
import com.optimus.redis.support.RedisCfg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XmlReaderUtil {

    private static final Logger logger = LoggerFactory.getLogger(XmlReaderUtil.class);
    private static final String CFG_HOST = "host";
    private static final String CFG_PORT = "port";
    private static final String CFG_MASTER = "master";

    private static final String HOST_PATTERN = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";
    private static Resource resource;

    public static List<RedisCfg> parseXml(String cfgPath) {
        try {
            resource = new ClassPathResource(cfgPath);
            URL url = resource.getURL();
            InputStream in = url.openStream();
            DocumentBuilder documentBuilder = getDocumentBuilder();
            Document document = documentBuilder.parse(in);
            NodeList nodeList = document.getElementsByTagName("node");
            if(nodeList == null || nodeList.getLength() == 0){
                logger.info("no redis configuration");
                return null;
            }

            List<RedisCfg> cfgs = new ArrayList<>();
            Map<String,RedisCfg> masterSlaveMapping = new LinkedHashMap<>();
            int len = nodeList.getLength();
            for (int i = 0;i < len;i++){
                Node node = nodeList.item(i);
                NamedNodeMap attributes = node.getAttributes();
                if(attributes == null || attributes.getLength() <= 0){
                    logger.info("redis configure failure");
                    throw new BizException("redis 配置错误");
                }

                Node hostNode = attributes.getNamedItem("host");
                String host = handleHostNode(hostNode);

                Node portNode = attributes.getNamedItem("port");
                Integer port = handlePortNode(portNode);

                String slaveId = handleSlaveNode(attributes.getNamedItem("slave-id"));

                if(masterSlaveMapping.containsKey(nodeKey(host,port))){
                    throw new BizException("redis:配置错误,["+nodeKey(host,port)+"]节点重复配置");
                }
                RedisCfg cfg = new RedisCfg();
                cfg.setHost(host);
                cfg.setPort(port);
                cfg.setSlaveId(slaveId);

                cfgs.add(cfg);
                masterSlaveMapping.put(nodeKey(cfg.getHost(),cfg.getPort()),cfg);
            }

            // 验证主从配置是否正确
            for (RedisCfg redisCfg : cfgs){
                String id = redisCfg.getSlaveId();
                if(Utils.isEmpty(id)){
                    continue;
                }

                if(!masterSlaveMapping.containsKey(id)){
                    throw new BizException("redis: 主从配置不正确,未发现["+id+"]的从节点配置");
                }

                redisCfg.setRedisSlaveCfg(masterSlaveMapping.get(id));
            }

            in.close();
            return cfgs;
        } catch (Exception e){
            throw new BizException(e);
        }
    }

    private static String nodeKey(String host,int port){
        return host+":"+port;
    }

    private static String handleSlaveNode(Node slave) {
        if(slave == null){
            return null;
        }

        return slave.getNodeValue();
    }

    private static Integer handlePortNode(Node portNode) {
        if(portNode == null){
            throw new BizException("redis:port配置不能为空");
        }

        String portValue = portNode.getNodeValue();
        if(!portValue.matches("\\d+")){
            throw new BizException("redis:port配置错误");
        }

        int port = Integer.valueOf(portValue);
        if(port <= 0){
            throw new BizException("redis:port配置错误");
        }
        return port;
    }

    private static String handleHostNode(Node hostNode) {
        if(hostNode == null){
            throw new BizException("redis:host配置不能为空");
        }

        String hostValue = hostNode.getNodeValue();
        if(!hostValue.matches(HOST_PATTERN)){
            throw new BizException("redis:host配置错误");
        }

        return hostValue;
    }

    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException{
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public static void main(String[] args){
        parseXml("redis-cfg.xml");
    }

}
