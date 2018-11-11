package cn.chenmixuexi.sqlfactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlSessionFactory {
    private String driver;
    private String url;
    private String name;
    private String password;
    private String interfacePath;
    private String sqlXmlFilePath;
    private DataSource dataSource;


    public MySqlSession openSession() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        return null;
    }

    /**
     * 是否自动提交事务
     * @param b
     * @return
     * @throws SQLException
     */
    public MySqlSession openSession(boolean b) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(b);
        return null;
    }

}
