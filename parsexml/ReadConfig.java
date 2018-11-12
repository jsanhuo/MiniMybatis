package cn.chenmixuexi.parsexml;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;

public class ReadConfig {

    public static NodeList readConfig(InputStream inputStream){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder= documentBuilderFactory.newDocumentBuilder();
            Document parse = documentBuilder.parse(inputStream);
            return parse.getDocumentElement().getChildNodes();
        } catch (Exception e) {
            System.err.println("构造解析器出错");
        }
        return null;
    }

    public static NodeList readConfig(File file){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder= documentBuilderFactory.newDocumentBuilder();
            Document parse = documentBuilder.parse(file);
            return parse.getDocumentElement().getChildNodes();
        } catch (Exception e) {
            System.err.println("构造解析器出错");
        }
        return null;
    }


}
