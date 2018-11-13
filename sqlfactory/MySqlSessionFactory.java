package cn.chenmixuexi.sqlfactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlSessionFactory {
    private ArrayList<String> interfacePath;
    private ArrayList<String> sqlXmlFilePath;
    private DataSource dataSource;

    public MySqlSessionFactory() {
    }
    public MySqlSessionFactory(ArrayList<String> interfacePath, ArrayList<String> sqlXmlFilePath, DataSource dataSource) {
        this.interfacePath = interfacePath;
        this.sqlXmlFilePath = sqlXmlFilePath;
        this.dataSource = dataSource;
    }
    public MySqlSession openSession() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        return new MySqlSession(connection);
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
        return new MySqlSession(connection);
    }

}
