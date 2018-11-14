package cn.chenmixuexi.util;

import cn.chenmixuexi.Proxy.bean.CacheBean;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {
    public static void CleanCache(String tableName, ArrayList<ConcurrentHashMap<CacheBean, Object>> firstLevelCacheList, ConcurrentHashMap<CacheBean,Object> SecondLevelCache){
        if(firstLevelCacheList!=null&&firstLevelCacheList.size()!=0){
            int len=firstLevelCacheList.size();
            for (int i=0;i<len;i++){
                ConcurrentHashMap<CacheBean, Object> firstLevelCache = firstLevelCacheList.get(i);
                for (CacheBean cacheBean:firstLevelCache.keySet()){
                    if(cacheBean!=null&&tableName.equals(cacheBean.getTableName())){
                        firstLevelCache.put(cacheBean,CacheBean.NULL_CACHE_BEAN);
                    }
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
