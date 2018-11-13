package cn.chenmixuexi.util;

import cn.chenmixuexi.Proxy.bean.CacheBean;

import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {
    public static void CleanCache(String tableName,ConcurrentHashMap<CacheBean,Object> firstLevelCache,ConcurrentHashMap<CacheBean,Object> SecondLevelCache){
        if(firstLevelCache!=null){
            for (CacheBean cacheBean:firstLevelCache.keySet()){
                if(cacheBean.getTableName().equals(tableName)){
                    firstLevelCache.put(cacheBean,null);
                }
            }
        }
        if(SecondLevelCache!=null){
            for (CacheBean cacheBean:SecondLevelCache.keySet()){
                if(cacheBean.getTableName().equals(tableName)){
                    SecondLevelCache.put(cacheBean,null);
                }
            }
        }
    }
}
