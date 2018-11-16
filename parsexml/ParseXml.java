package cn.chenmixuexi.parsexml;

import cn.chenmixuexi.parsexml.bean.XmlBean;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseXml {
    public static XmlBean ParseXml(InputStream inputStream) {
        NodeList nodeList = ReadConfig.readConfig(inputStream);
        return init(nodeList);
    }

    public static XmlBean ParseXml(File file) {
        NodeList nodeList = ReadConfig.readConfig(file);
        return init(nodeList);
    }

    private static XmlBean init(NodeList nodeList) {
        HashMap<String, Node> nodeHashMap = SelectEM.SelectEt(nodeList);
        HashMap<String, ArrayList<String>> mappers = MappersToMap.NodeToMap(nodeHashMap.get("mappers"));
        HashMap<String, String> environment = EnvironmentToMap.NodeToMap(nodeHashMap.get("environment"));
        return new XmlBean(mappers, environment);
    }
}
