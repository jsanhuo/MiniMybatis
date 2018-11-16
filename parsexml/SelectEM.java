package cn.chenmixuexi.parsexml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

public class SelectEM {
    public static HashMap<String, Node> SelectEt(NodeList nodeList) {
        int length = nodeList.getLength();
        if (length >= 0) {
            HashMap<String, Node> nodeHashMap = new HashMap<String, Node>();
            for (int i = 0; i < length; i++) {
                Node item = nodeList.item(i);
                if (item instanceof Element) {
                    if (item.getNodeName().equals("environment")) {
                        nodeHashMap.put("environment", item);
                    }
                    if (item.getNodeName().equals("mappers")) {
                        nodeHashMap.put("mappers", item);
                    }
                }
            }
            return nodeHashMap;
        } else {
            System.err.println("配置文件格式错误");
        }
        return null;
    }
}
