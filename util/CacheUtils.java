package cn.chenmixuexi.util;

import cn.chenmixuexi.Proxy.bean.CacheBean;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class CacheUtils {
    public static void CleanCache(String tableName, ArrayList<ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>>> firstLevelCacheList, ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>> SecondLevelCache) {
        if (firstLevelCacheList != null && firstLevelCacheList.size() != 0) {
            int len = firstLevelCacheList.size();
            for (int i = 0; i < len; i++) {
                ConcurrentHashMap<String, ConcurrentHashMap<CacheBean, Object>> firstLevelCache = firstLevelCacheList.get(i);
                if (firstLevelCache.get(tableName) != null) {
                    for (CacheBean cacheBean : firstLevelCache.get(tableName).keySet()) {
                        if (cacheBean != null && tableName.equals(cacheBean.getTableName())) {
                            firstLevelCache.get(tableName).put(cacheBean, CacheBean.NULL_CACHE_BEAN);
                        }
                    }
                }
            }
        }
        if (SecondLevelCache != null) {
            if (SecondLevelCache.get(tableName) != null) {
                for (CacheBean cacheBean : SecondLevelCache.get(tableName).keySet()) {
                    if (cacheBean != null && tableName.equals(cacheBean.getTableName())) {
                        SecondLevelCache.get(tableName).put(cacheBean, CacheBean.NULL_CACHE_BEAN);
                    }
                }
            }
        }
    }
}
