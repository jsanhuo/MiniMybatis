package cn.chenmixuexi.Proxy;

import cn.chenmixuexi.Proxy.bean.CacheBean;
import cn.chenmixuexi.annotation.MyDelete;
import cn.chenmixuexi.annotation.MyInsert;
import cn.chenmixuexi.annotation.MySelect;
import cn.chenmixuexi.annotation.MyUpdate;
import cn.chenmixuexi.util.BeanUtils;
import cn.chenmixuexi.util.CacheUtils;
import cn.chenmixuexi.util.SqlUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapperInvocationHandler implements InvocationHandler {
    //一级缓存
    private static ArrayList<ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>>> firstLevelCacheList = new ArrayList<ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>>>();
    //二级缓存
    private static ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>> SecondLevelCache = new ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>>();
    private Connection connection;
    private ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>> firstLevelCache;


    public MapperInvocationHandler(Connection connection, ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>> firstLevelCache) {
        this.connection = connection;
        this.firstLevelCache = firstLevelCache;
        firstLevelCacheList.add(firstLevelCache);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // method
        // 通过反射访问method的注解 @MySelect
        // @MySelect value => select * from student where id = #{id}
        // 解析args，把参数拿出来，填到上面的sql语句中
        // Preparestatement => server  => sql
        // 服务器返回的student记录打包成student对象，返回回去
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        for (Annotation a :
                declaredAnnotations) {
            //查找操作
            if (a instanceof MySelect) {
                try {
                    return SelectOpertion(a, method, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Select操作错误");
                }
            }
            //插入操作
            if (a instanceof MyInsert) {
                try {
                    return InsertOpertion(a, method, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Insert操作错误");
                }
            }
            //删除操作
            if (a instanceof MyDelete) {
                try {
                    return DeleteOpertion(a, method, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Delete操作错误");
                }
            }
            //修改操作
            if (a instanceof MyUpdate) {
                try {
                    return UpdateOpertion(a, method, args);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Update操作错误");
                }
            }
        }
        return null;
    }

    private Object UpdateOpertion(Annotation a, Method method, Object[] args) throws SQLException {
        String value = ((MyUpdate) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);
        String tableName = s.split(" ")[1];
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        int len = args.length;
        for (int i = 0; i < len; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        int execute = preparedStatement.executeUpdate();
        if (execute >= 1) {
            CacheUtils.CleanCache(tableName, firstLevelCacheList, SecondLevelCache);
        }
        return execute >= 1;
    }

    private Object DeleteOpertion(Annotation a, Method method, Object[] args) throws SQLException {
        String value = ((MyDelete) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);
        String tableName = s.split(" ")[2];
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        int len = args.length;
        for (int i = 0; i < len; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        int execute = preparedStatement.executeUpdate();
        if (execute >= 1) {
            CacheUtils.CleanCache(tableName, firstLevelCacheList, SecondLevelCache);
        }
        return execute >= 1;
    }

    private Object InsertOpertion(Annotation a, Method method, Object[] args) throws SQLException {
        String value = ((MyInsert) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);
        String tableName = s.split(" ")[2];
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        int len = args.length;
        for (int i = 0; i < len; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        int execute = preparedStatement.executeUpdate();
        if (execute >= 1) {
            CacheUtils.CleanCache(tableName, firstLevelCacheList, SecondLevelCache);
        }
        return execute >= 1;
    }


    public Object SelectOpertion(Annotation a, Method method, Object[] args) throws SQLException {
        String value = ((MySelect) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);
        String tableName = s.split(" ")[3];
        CacheBean cacheBean = new CacheBean(s, args);
        Object o1 = null;
        ConcurrentHashMap<CacheBean, Object> cacheBeanObjectConcurrentHashMap = firstLevelCache.get(tableName);
        if (cacheBeanObjectConcurrentHashMap != null) {
            o1 = cacheBeanObjectConcurrentHashMap.get(cacheBean);
        }
        if (o1 != null && !CacheBean.NULL_CACHE_BEAN.equals(o1)) {
            System.out.println("通过一级缓存访问成功");
            return o1;
        }
        ConcurrentHashMap<CacheBean, Object> cacheBeanObjectConcurrentHashMap1 = SecondLevelCache.get(tableName);
        if (cacheBeanObjectConcurrentHashMap1 != null) {
            o1 = cacheBeanObjectConcurrentHashMap1.get(cacheBean);
        }
        if (o1 != null && !CacheBean.NULL_CACHE_BEAN.equals(o1)) {
            System.out.println("通过二级缓存访问成功");
            ConcurrentHashMap<CacheBean, Object> first = firstLevelCache.get(tableName);
            if (first != null) {
                first.put(cacheBean, o1);
            } else {
                ConcurrentHashMap<CacheBean, Object> temp = new ConcurrentHashMap<>();
                temp.put(cacheBean, o1);
                firstLevelCache.put(tableName, temp);
            }
            return o1;
        }
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> returnType = method.getReturnType();
        boolean tag = false;
        //获得底层方法的正式返回类型如ArrayList<IStudent> 返回即为 java.util.List<MyMyBatis.IStudent>
        Type genericReturnType = method.getGenericReturnType();
        //判断其是否为泛型化的字段
        if (genericReturnType instanceof ParameterizedType) {
            //获得其中的MyMyBatis.IStudent
            returnType = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
            tag = true;
        }
        ArrayList<HashMap<String, Object>> hashMaps = BeanUtils.resultToMap(returnType, resultSet);
        Object o = null;
        if (hashMaps.size() == 1 && !tag) {
            o = BeanUtils.builderObject(returnType, hashMaps.get(0));
        } else if (hashMaps.size() >= 1 && tag) {
            o = BeanUtils.builderObjectList(returnType, hashMaps);
        } else {
            System.out.println("无数据");
            return null;
        }
        ConcurrentHashMap<CacheBean, Object> first = firstLevelCache.get(tableName);
        if (first != null) {
            first.put(cacheBean, o);
        } else {
            ConcurrentHashMap<CacheBean, Object> temp = new ConcurrentHashMap<>();
            temp.put(cacheBean, o);
            firstLevelCache.put(tableName, temp);
        }
        ConcurrentHashMap<CacheBean, Object> second = SecondLevelCache.get(tableName);
        if (second != null) {
            second.put(cacheBean, o);
        } else {
            ConcurrentHashMap<CacheBean, Object> temp = new ConcurrentHashMap<>();
            temp.put(cacheBean, o);
            SecondLevelCache.put(tableName, temp);
        }
        return o;
    }


}


