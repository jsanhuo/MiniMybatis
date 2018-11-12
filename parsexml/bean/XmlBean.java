package cn.chenmixuexi.parsexml.bean;

import java.util.ArrayList;
import java.util.HashMap;

public class XmlBean {
    private HashMap<String, ArrayList<String>> mappers;
    private HashMap<String, String> environment;

    public XmlBean() {
    }

    public XmlBean(HashMap<String, ArrayList<String>> mappers, HashMap<String, String> environment) {
        this.mappers = mappers;
        this.environment = environment;
    }

    public HashMap<String, ArrayList<String>> getMappers() {
        return mappers;
    }

    public HashMap<String, String> getEnvironment() {
        return environment;
    }
}
