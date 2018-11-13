package cn.chenmixuexi.Proxy;

import cn.chenmixuexi.annotation.MyDelete;
import cn.chenmixuexi.annotation.MyInsert;
import cn.chenmixuexi.annotation.MySelect;
import cn.chenmixuexi.annotation.MyUpdate;
import cn.chenmixuexi.util.BeanUtils;
import cn.chenmixuexi.util.CacheUtils;
import cn.chenmixuexi.util.SqlUtils;
import cn.chenmixuexi.Proxy.bean.CacheBean;
import com.sun.javaws.CacheUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MapperInvocationHandler implements InvocationHandler {
    private Connection connection;
    //一级缓存
    private ConcurrentHashMap<CacheBean,Object> firstLevelCache;
    //二级缓存
    private static ConcurrentHashMap<CacheBean,Object> SecondLevelCache = new ConcurrentHashMap<CacheBean,Object>();

    public MapperInvocationHandler(Connection connection, ConcurrentHashMap<CacheBean, Object> firstLevelCache) {
        this.connection = connection;
        this.firstLevelCache = firstLevelCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)  {
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
            if(a instanceof MySelect){
                try {
                    return SelectOpertion(a,method,args);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Select操作错误");
                }
            }
            //插入操作
            if(a instanceof MyInsert){
                return InsertOpertion(a,method,args);
            }
            //删除操作
            if(a instanceof MyDelete){
                return DeleteOpertion(a,method,args);
            }
            //修改操作
            if(a instanceof MyUpdate){
                return UpdateOpertion(a,method,args);
            }
        }
        return null;
    }

    private Object UpdateOpertion(Annotation a, Method method, Object[] args) {
        String value = ((MyUpdate) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);

        return null;
    }

    private Object DeleteOpertion(Annotation a, Method method, Object[] args) {
        String value = ((MyDelete) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);
        return null;
    }

    private Object InsertOpertion(Annotation a, Method method, Object[] args) {
        String value = ((MyInsert) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);
        String tableName = s.split(" ")[2];
        CacheUtils.CleanCache(tableName,firstLevelCache,SecondLevelCache);
        Class<?> parameter = method.getParameterTypes()[0];
        Object x = args[0];

        return true;
    }

    /**
     * Select操作
     * @param a         注解
     * @param method    方法
     * @param args      参数列表
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws SQLException
     */
    public Object SelectOpertion(Annotation a,Method method,Object[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        String value = ((MySelect) a).value();
        //进行SQL的处理
        String s = SqlUtils.builderSql(value);
        CacheBean cacheBean = new CacheBean(s, args);
        Object o1 = firstLevelCache.get(cacheBean);
        if(firstLevelCache.get(cacheBean)!=null){
            System.out.println("通过一级缓存访问成功");
            return o1;
        }
        o1 = SecondLevelCache.get(cacheBean);
        if(o1!=null){
            System.out.println("通过二级缓存访问成功");
            return o1;
        }
        PreparedStatement preparedStatement = connection.prepareStatement(s);
        if(args!=null){
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        Class<?> returnType = method.getReturnType();
        //获得底层方法的正式返回类型如ArrayList<IStudent> 返回即为 java.util.List<MyMyBatis.IStudent>
        Type genericReturnType = method.getGenericReturnType();
        //判断其是否为泛型化的字段
        if(genericReturnType instanceof ParameterizedType){
            //获得其中的MyMyBatis.IStudent
            returnType = (Class) ((ParameterizedType)genericReturnType).getActualTypeArguments()[0];
        }
        ArrayList<HashMap<String, Object>> hashMaps = BeanUtils.resultToMap(returnType, resultSet);
        Object o=null;
        if(hashMaps.size()==1){
            o = BeanUtils.builderObject(returnType, hashMaps.get(0));
        }else{
            o = BeanUtils.builderObjectList(returnType, hashMaps);
        }
        firstLevelCache.put(cacheBean,o);
        SecondLevelCache.put(cacheBean,o);
        return o;
    }







}


