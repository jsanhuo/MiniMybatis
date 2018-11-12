package cn.chenmixuexi.parsexml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class MappersToMap {
    public static HashMap<String, ArrayList<String>> NodeToMap(Node node){
        if(node==null){
            return null;
        }
        NodeList childNodes = node.getChildNodes();
        HashMap<String,  ArrayList<String>> mappers = new HashMap<>();
        ArrayList<String> resources = new ArrayList<String>();
        ArrayList<String> classs = new ArrayList<String>();
        int len = childNodes.getLength();
        for (int i = 0; i < len; i++) {
            Node item = childNodes.item(i);
            if(item instanceof Element){
                String resource = ((Element) item).getAttribute("resource");
                if(!resource.equals("")){
                    resources.add(resource);
                }
                resource = ((Element) item).getAttribute("class");
                if(!resource.equals("")){
                    classs.add(resource);
                }
            }
        }
        mappers.put("class",classs);
        mappers.put("resource",resources);
        return mappers;
    }
}
