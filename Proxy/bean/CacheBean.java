package cn.chenmixuexi.Proxy.bean;

import java.util.Arrays;
import java.util.Objects;

public class CacheBean {
    private String sql;
    private Object[] args;
    private String tableName;

    public CacheBean(String sql, Object[] args) {
        this.sql = sql;
        this.args = args;
        tableName = sql.split(" ")[3];
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheBean cacheBean = (CacheBean) o;
        return Objects.equals(sql, cacheBean.sql) &&
                Arrays.equals(args, cacheBean.args);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(sql);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
