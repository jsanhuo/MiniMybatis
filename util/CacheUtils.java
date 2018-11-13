package cn.chenmixuexi.util;

import cn.chenmixuexi.Proxy.bean.CacheBean;

import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {
    public static void CleanCache(String tableName,ConcurrentHashMap<CacheBean,Object> firstLevelCache,ConcurrentHashMap<CacheBean,Object> SecondLevelCache){
        if(firstLevelCache!=null){
            for (CacheBean cacheBean:firstLevelCache.keySet()){
                if(cacheBean!=null&&tableName.equals(cacheBean.getTableName())){
                    firstLevelCache.put(cacheBean,CacheBean.NULL_CACHE_BEAN);
                }
            }
        }
        if(SecondLevelCache!=null){
            for (CacheBean cacheBean:SecondLevelCache.keySet()){
                if(cacheBean!=null&&tableName.equals(cacheBean.getTableName())){
                    SecondLevelCache.put(cacheBean,CacheBean.NULL_CACHE_BEAN);
                }
            }
        }
    }
}
