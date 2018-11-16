package com.optimus.redis.config.reader.support;

import com.optimus.redis.RedisConfigRegistry;
import com.optimus.redis.config.RedisConfig;
import com.optimus.redis.config.reader.RedisConfigReader;
import com.optimus.utils.Utils;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlConfigReader implements RedisConfigReader {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final String REDIS_CONFIG_TAG = "redis-config";
    private static final String REDIS_NODE_TAG = "node";
    private static final String REDIS_SLAVES_TAG = "redis-slaves";
    private static final String ATTR_HOST_TAG = "host";
    private static final String ATTR_PORT_TAG = "port";
    private static final String ATTR_AUTH_TAG = "auth";
    private static final String ATTR_READ_ONLY_TAG = "readOnly";

    private Resource resource;


    public XmlConfigReader(String path) {
        resource = new ClassPathResource(path);

    }

    @Override
    public void read(RedisConfigRegistry registry) {

        try {

            URL url = resource.getURL();
            InputStream in = url.openStream();
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(in);

            NodeList redisConfigNodes = document.getElementsByTagName(REDIS_CONFIG_TAG);
            if(redisConfigNodes == null || redisConfigNodes.getLength() < 1) {
                logger.info("Not found redis config info");
                return;
            }

            int rcnSize = redisConfigNodes.getLength();
            for (int i = 0; i < rcnSize; i++) {
                Node node = redisConfigNodes.item(i);
                NodeList nodeList = node.getChildNodes();
                if(nodeList == null || nodeList.getLength() < 1) {
                    continue;
                }

                int nlSize = nodeList.getLength();
                for (int k = 0; k < nlSize; k++) {
                    Node ccNode = nodeList.item(k);
                    if(Objects.equals(ccNode.getNodeName(),REDIS_NODE_TAG)) {
                        RedisConfig config = handleRedisNode(ccNode,false);
                        registry.register(config);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private RedisConfig handleRedisNode(Node redisNode,boolean isSlave) {

        if(redisNode == null) {
            return null;
        }

        RedisConfig nodeConfig = handleAttrNode(redisNode,isSlave);

        if(nodeConfig == null) {
            return null;
        }

        // 处理从节点
        NodeList slaveConfigs = redisNode.getChildNodes();
        if(slaveConfigs != null && slaveConfigs.getLength() > 0) {
            Node redisSlavesNode = null;
            for (int i = 0; i<slaveConfigs.getLength();i++) {
                Node node = slaveConfigs.item(i);
                if(Objects.equals(REDIS_SLAVES_TAG,node.getNodeName())) {
                    redisSlavesNode = node;
                    break;
                }
            }

            if(redisSlavesNode != null) {
                NodeList slaveNodes = redisSlavesNode.getChildNodes();
                if(slaveNodes != null && slaveNodes.getLength() > 0) {
                    for (int k = 0; k < slaveNodes.getLength();k++) {
                        Node slaveNode = slaveNodes.item(k);
                        if(Objects.equals(REDIS_NODE_TAG,slaveNode.getNodeName())) {
                            nodeConfig.setSlaveConfig(handleRedisNode(slaveNode,true));
                        }
                    }
                }
            }
        }

        return nodeConfig;
    }

    private RedisConfig handleAttrNode(Node node, boolean isSlave) {
        // 处理节点属性
        NamedNodeMap attributes = node.getAttributes();
        if(attributes == null || attributes.getLength() < 1) {
            logger.error("redis config error, lose host and port info and so on");
            throw new RuntimeException("[redis配置错误]必要信息均未配置");
        }

        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setSlave(isSlave);
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attrNode = attributes.item(i);
            String attrName = attrNode.getNodeName();
            if(Objects.equals(ATTR_HOST_TAG,attrName)) {
                redisConfig.setHost(attrNode.getNodeValue());
            } else if(Objects.equals(ATTR_PORT_TAG,attrName)) {
                redisConfig.setPort(Integer.valueOf(attrNode.getNodeValue()));
            } else if(Objects.equals(ATTR_AUTH_TAG,attrName)) {
                redisConfig.setAuth(attrNode.getNodeValue());
            } else if(Objects.equals(ATTR_READ_ONLY_TAG,attrName)) {
                if(StringUtils.isEmpty(attrNode.getNodeValue())) {
                    redisConfig.setReadOnly(false);
                } else {
                    redisConfig.setReadOnly(Boolean.valueOf(attrNode.getNodeValue()));
                }
            }
        }

        return redisConfig;
    }

    public static void main(String[] args) {
        RedisConfigReader reader = new XmlConfigReader("redis-config.xml");
        RedisConfigRegistry redisConfigRegistry = new RedisConfigRegistry();
        reader.read(redisConfigRegistry);
        System.out.println(Utils.toJson(redisConfigRegistry));
    }
}
