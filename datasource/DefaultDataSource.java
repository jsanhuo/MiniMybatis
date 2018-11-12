package cn.chenmixuexi.datasource;

import cn.chenmixuexi.datasource.abs.absDefaultDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DefaultDataSource extends absDefaultDataSource {
    private String dirverClassName;
    private String url;
    private String user;
    private String pswd;

    public DefaultDataSource(String dirverClassName, String url, String user, String pswd) {
        this.dirverClassName = dirverClassName;
        this.url = url;
        this.user = user;
        this.pswd = pswd;
        init();
    }

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

    private Connection makeConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pswd);
    }
}
