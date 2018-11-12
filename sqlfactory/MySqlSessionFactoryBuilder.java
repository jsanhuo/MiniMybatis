package cn.chenmixuexi.sqlfactory;

import cn.chenmixuexi.datasource.DefaultDataSource;
import cn.chenmixuexi.parsexml.ParseXml;
import cn.chenmixuexi.parsexml.bean.XmlBean;

import javax.sql.DataSource;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MySqlSessionFactoryBuilder {
    /**
     * 用户从config.xml中读取配置，放到一个SQLSessionFactory中
     * 该方法主要是再InputStream中读取
     * driver url username password
     * @param in
     * @return
     */
    public MySqlSessionFactory build(InputStream in) throws Exception {
        XmlBean xmlBean = ParseXml.ParseXml(in);
        HashMap<String, String> environment = xmlBean.getEnvironment();
        HashMap<String, ArrayList<String>> mappers = xmlBean.getMappers();
        if(environment==null&&mappers==null){
            throw new Exception("配置文件出错,加载MySqlSessionFactory出错");
        }
        if(environment!=null&&mappers!=null){
            DefaultDataSource defaultDataSource = new DefaultDataSource(environment.get("driver"),environment.get("url"),environment.get("username"),environment.get("password"));
            return new MySqlSessionFactory(mappers.get("class"),mappers.get("resource"),defaultDataSource);
        }else{
            throw new Exception("配置文件出错,加载MySqlSessionFactory出错");
        }
    }

    public MySqlSessionFactory build(File in) throws Exception {
        XmlBean xmlBean = ParseXml.ParseXml(in);
        HashMap<String, String> environment = xmlBean.getEnvironment();
        HashMap<String, ArrayList<String>> mappers = xmlBean.getMappers();
        if(environment==null&&mappers==null){
            throw new Exception("配置文件出错,加载MySqlSessionFactory出错");
        }
        if(environment!=null&&mappers!=null){
            DefaultDataSource defaultDataSource = new DefaultDataSource(environment.get("driver"),environment.get("url"),environment.get("username"),environment.get("password"));
            return new MySqlSessionFactory(mappers.get("class"),mappers.get("resource"),defaultDataSource);
        }else{
            throw new Exception("配置文件出错,加载MySqlSessionFactory出错");
        }
    }



    public MySqlSessionFactory build(InputStream in, DataSource dataSource) throws Exception {
        XmlBean xmlBean = ParseXml.ParseXml(in);
        HashMap<String, String> environment = xmlBean.getEnvironment();
        HashMap<String, ArrayList<String>> mappers = xmlBean.getMappers();
        if(mappers==null){
            throw new Exception("配置文件出错,加载MySqlSessionFactory出错");
        }else {
            return new MySqlSessionFactory(mappers.get("class"),mappers.get("resource"),dataSource);
        }
    }
}
