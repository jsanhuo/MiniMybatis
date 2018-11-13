package cn.chenmixuexi.sqlfactory;

import cn.chenmixuexi.Proxy.MapperInvocationHandler;
import cn.chenmixuexi.Proxy.bean.CacheBean;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class MySqlSession {
    private ConcurrentHashMap<CacheBean,Object> firstLevelCache;
    private Connection connection;

    public MySqlSession(Connection connection) {
        this.connection = connection;
        firstLevelCache = new ConcurrentHashMap<CacheBean,Object>();
    }
    /**
     * 提交事务
     * @throws SQLException
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            System.err.println("提交失败");
        }
    }

    public void roolback()  {
        try {
            connection.rollback();
        } catch (SQLException e) {
            System.err.println("回滚失败");
        }
    }
    /**
     * 关闭连接
     */
    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("关闭连接失败");
        }
    }
    public <T> T getMapper(Class<T> mapp){
        if(!mapp.isInterface()){
            throw new IllegalArgumentException(mapp+"is not an interface");
        }
        return (T)Proxy.newProxyInstance(mapp.getClassLoader(),new Class[]{mapp},new MapperInvocationHandler(connection,firstLevelCache));
    }
}
