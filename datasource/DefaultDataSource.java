package cn.chenmixuexi.datasource;

import cn.chenmixuexi.datasource.abs.absDefaultDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 默认的数据源(运用原生的JDBC封装的数据源)
 * @author 焦焱
 */
public class DefaultDataSource extends absDefaultDataSource {
    private String dirverClassName;
    private String url;
    private String user;
    private String pswd;

    /**
     * 构造函数
     * @param dirverClassName
     * @param url
     * @param user
     * @param pswd
     */
    public DefaultDataSource(String dirverClassName, String url, String user, String pswd) {
        this.dirverClassName = dirverClassName;
        this.url = url;
        this.user = user;
        this.pswd = pswd;
        init();
    }

    /**
     * 初始化
     */
    private void init(){
        try {
            Class.forName(dirverClassName);
        } catch (ClassNotFoundException e) {
            System.out.println("加载类驱动错误");
        }
    }
    /**
     * 获取一个数据库连接
     *
     * @return 一个数据库连接
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return makeConnection();
    }

    /**
     * 建立连接
     * @return  JDBC连接
     * @throws SQLException
     */
    private Connection makeConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pswd);
    }
}
