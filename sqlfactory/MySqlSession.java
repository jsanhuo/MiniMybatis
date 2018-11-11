package cn.chenmixuexi.sqlfactory;

import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

public class MySqlSession {
    private ConcurrentHashMap<String,Object> firstLevelCache;
    /**
     * 提交事务
     * @throws SQLException
     */
    public void commit() throws SQLException {
    }

    public void roolback(){
    }
    /**
     * 关闭连接
     */
    public void close(){
    }


    public <T> T getMapper(Class<T> mapp){
        return null;
    }
}
