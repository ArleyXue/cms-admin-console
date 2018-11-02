package com.arley.cms.console.shiro;

import com.arley.cms.console.component.RedisDao;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 重写Shiro缓存管理器
 * @author Wang926454
 * @date 2018/9/4 17:41
 */
public class CustomCacheManager implements CacheManager {

    private RedisDao redisDao;

    public CustomCacheManager(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        CustomCache<K, V> kvCustomCache = new CustomCache<>();
        kvCustomCache.setRedisDao(redisDao);
        return kvCustomCache;
    }
}