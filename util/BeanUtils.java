package cn.chenmixuexi.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class BeanUtils {

    /**
     * 将一个Result转换为ArrayList<HashMap<String, Object>>对象
     * @param clazz         对象的Class
     * @param resultSet     结果集
     * @return
     * @throws SQLException
     */
    public static ArrayList<HashMap<String, Object>> resultToMap(Class clazz, ResultSet resultSet) throws SQLException {
        Field[] declaredFields = clazz.getDeclaredFields();
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
        while (resultSet.next()){
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            for (int i = 0; i < declaredFields.length; i++) {
                String name = declaredFields[i].getName();
                stringObjectHashMap.put(name,resultSet.getObject(name));
            }
            hashMaps.add(stringObjectHashMap);
        }
        return hashMaps;
    }

    /**
     * 构造对象的List集合
     * @param oClass
     * @param maps
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static <T> ArrayList<T>  builderObjectList(Class<T> oClass, ArrayList<HashMap<String, Object>> maps) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        ArrayList<T> ts = new ArrayList<>();
        int len = maps.size();
        for (int i = 0; i < len; i++) {
            T t = builderObject(oClass, maps.get(i));
            ts.add(t);
        }
        return ts;
    }

    /**
     * 进行类的构造
     * @param oClass
     * @param map
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     */
    public static <T> T  builderObject(Class<T> oClass, HashMap<String,Object> map) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T t = oClass.newInstance();
        Field[] declaredFields = oClass.getDeclaredFields();
        for (Field f :
                declaredFields) {
            f.setAccessible(true);
            f.set(t,map.get(f.getName()));
        }
        return t;
    }
}
