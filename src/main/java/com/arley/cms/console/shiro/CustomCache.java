package com.arley.cms.console.shiro;

import com.arley.cms.console.component.RedisDao;
import com.arley.cms.console.constant.PublicConstants;
import com.arley.cms.console.pojo.vo.AdminTokenVO;
import com.arley.cms.console.util.FastJsonUtils;
import com.arley.cms.console.util.JJWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 重写Shiro的Cache保存读取
 * @author Wang926454
 * @date 2018/9/4 17:31
 */
public class CustomCache<K,V> implements Cache<K,V> {

    private RedisDao redisDao;

    public void setRedisDao(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    /**
     * 缓存的key名称获取为shiro:cache:account
     * @param key
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/4 18:33
     */
    private String getKey(String key){
        Claims claims = JJWTUtils.parseJWT(key);
        String subject = claims.getSubject();
        AdminTokenVO adminTokenVO = FastJsonUtils.json2Bean(subject, AdminTokenVO.class);
        return PublicConstants.PREFIX_SHIRO_CACHE + adminTokenVO.getUserName();
    }

    /**
     * 获取缓存
     */
    @Override
    public Object get(Object key) throws CacheException {
        if(!redisDao.hasKey(this.getKey(key.toString()))){
            return null;
        }
        return redisDao.get(this.getKey(key.toString()));
    }

    /**
     * 保存缓存
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        // 设置Redis的Shiro缓存
        System.out.println(this.getKey(key.toString()));
        return redisDao.set(this.getKey(key.toString()), value, PublicConstants.SHIRO_CACHE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 移除缓存
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if(!redisDao.hasKey(this.getKey(key.toString()))){
            return null;
        }
        redisDao.delete(this.getKey(key.toString()));
        return null;
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        Set<String> keys = redisDao.keys(PublicConstants.PREFIX_SHIRO_CACHE + "*");
        redisDao.delete(keys);
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Set<String> keys = redisDao.keys(PublicConstants.PREFIX_SHIRO_CACHE + "*");
        return keys.size();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        return redisDao.keys(PublicConstants.PREFIX_SHIRO_CACHE + "*");
    }

    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            values.add(redisDao.get(this.getKey(key.toString())));
        }
        return values;
    }
}