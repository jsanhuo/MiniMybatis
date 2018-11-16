package cn.chenmixuexi.parsexml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;

public class EnvironmentToMap {
    public static HashMap<String, String> NodeToMap(Node node) {
        if (node == null) {
            return null;
        }
        NodeList childNodes = node.getChildNodes();
        HashMap<String, String> environment = new HashMap<>();
        int len = childNodes.getLength();
        for (int i = 0; i < len; i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                if (item.getNodeName().equals("property")) {
                    String name = ((Element) item).getAttribute("name");
                    String value = ((Element) item).getAttribute("value");
                    environment.put(name, value);
                }
            }

        }
        return environment;
    }
}
