package com.optimus.common;

import com.optimus.common.exception.BizException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;

/**
 * Created by Administrator on 2017/12/18.
 */
public class XmlParser {

    private static final String CONFIG_PATH = "redis-config.xml";
    private static Resource resource = new ClassPathResource(CONFIG_PATH);

    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static DocumentBuilder getDocumentBuilder(){
        try {
            return factory.newDocumentBuilder();
        } catch (Exception e) {
            throw new BizException(e);
        }
    }

    public static void load() throws IOException,SAXException {
        Document doc = getDocumentBuilder().parse(resource.getFile());
        NodeList nodeList = doc.getDocumentElement().getElementsByTagName("node");
        if(nodeList != null && nodeList.getLength() > 0){
            Node node = nodeList.item(0);
            NamedNodeMap namedNodeMap = node.getAttributes();
        }
    }
}
