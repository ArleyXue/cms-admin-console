package com.arley.cms.console.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDao {

    @Autowired
    private RedisTemplate redisTemplate;


    // ================String操作==================


    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(String key) {
        return StringUtils.isBlank(key) ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存 默认30天
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        return set(key, value, 30, TimeUnit.DAYS);
    }

    /**
     * 写入缓存设置时效时间, 如果要设置永久, expireTime 为0即可
     * @param key
     * @param value
     * @param expireTime 时间
     * @param timeUnit 时间类型
     * @return
     */
    public boolean set(String key, Object value, long expireTime, TimeUnit timeUnit) {
        if (expireTime > 0) {
            redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
        return true;
    }

    /**
     * 递增
     * @param key 键
     * @param num 要增加几(大于0)
     * @return
     */
    public long incr(String key, long num){
        if(num < 0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, num);
    }

    /**
     * 递减
     * @param key 键
     * @param num 要减少几(小于0)
     * @return
     */
    public long decr(String key, long num){
        if(num < 0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -num);
    }



    //================================MAP(哈希)操作=================================

    /**
     * 哈希 获取
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 哈希 获取对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<String, Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 哈希 添加map 如果要设置永久, expireTime 为0即可
     * @param key 键
     * @param map 对应多个键值
     * @param expireTime 时间
     * @param timeUnit 时间类型
     * @return true 成功 false 失败
     */
    public boolean hmsetAll(String key, Map<String, Object> map, long expireTime, TimeUnit timeUnit){
        redisTemplate.opsForHash().putAll(key, map);
        if (expireTime > 0) {
            expire(key, expireTime, timeUnit);
        }
        return true;
    }

    /**
     * 哈希 添加map 默认30天
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmsetAll(String key, Map<String, Object> map){
        hmsetAll(key, map, 30, TimeUnit.DAYS);
        return true;
    }

    /**
     * 哈希 添加 向一张hash表中放入数据,如果不存在将创建
     * @param key
     * @param item
     * @param value
     */
    public void hmSet(String key, String item, Object value){
        boolean flag = hasKey(key);
        if (!flag) {
            hset(key, item, value, 30, TimeUnit.DAYS);
        } else {
            hset(key, item, value, 0, null);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param expireTime 时间  注意:如果已存在的hash表有时间, 这里将会替换原有的时间 为0将不替换
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, item, value);
        if(expireTime > 0){
            expire(key, expireTime, timeUnit);
        }
        return true;
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, String... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 为散了中某个值加上 整型 delta
     * @param key
     * @param item
     * @param num
     */
    public void hIncrement(String key, String item, Integer num) {
        redisTemplate.opsForHash().increment(key, item, num);
    }

    //===============================list列表=================================

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */
    public List lGet(String key, long start, long end){
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public long lGetListSize(String key){
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key,long index){
        return redisTemplate.opsForList().index(key, index);
    }


    /**
     * 列表添加到后面 没有key则创建
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lRightPush(String key, Object value) {
        if (hasKey(key)) {
            lRightPush(key, value, 0, null);
        } else {
            lRightPush(key, value, 30, TimeUnit.DAYS);
        }
        return true;
    }

    /**
     * 列表添加到后面 如果expireTime>0 替换key时间
     * @param key 键
     * @param value 值
     * @param expireTime 时间
     * @param timeUnit 时间类型
     * @return
     */
    public boolean lRightPush(String key, Object value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForList().rightPush(key, value);
        if(expireTime > 0){
            expire(key, expireTime, timeUnit);
        }
        return true;
    }

    /**
     * 列表添加到前面 没有key则创建
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lLeftPush(String key, Object value) {
        if (hasKey(key)) {
            lLeftPush(key, value, 0, null);
        } else {
            lLeftPush(key, value, 30, TimeUnit.DAYS);
        }
        return true;
    }

    /**
     * 列表添加到前面 如果expireTime>0 替换key时间
     * @param key 键
     * @param value 值
     * @param expireTime 时间
     * @param timeUnit 时间类型
     * @return
     */
    public boolean lLeftPush(String key, Object value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForList().leftPush(key, value);
        if(expireTime > 0){
            expire(key, expireTime, timeUnit);
        }
        return true;
    }


    /**
     * 将list放入缓存 默认30天
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean lRightPushAll(String key, List<Object> value) {
        lRightPushAll(key, value, 30, TimeUnit.DAYS);
        return true;
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param expireTime 时间
     * @param timeUnit 时间类型
     * @return
     */
    public boolean lRightPushAll(String key, List<Object> value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForList().rightPushAll(key, value);
        if(expireTime > 0){
            expire(key, expireTime, timeUnit);
        }
        return true;
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
        return true;
    }


    //==========================set集合===========================

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void sAdd(String key,Object value){
        if (hasKey(key)) {
            sAdd(key, value, 0, null);
        } else {
            sAdd(key, value, 30, TimeUnit.DAYS);
        }
    }

    /**
     * 集合添加
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     */
    public void sAdd(String key, Object value, long time, TimeUnit timeUnit){
        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.add(key, value);
        if (time > 0) {
            expire(key, time, timeUnit);
        }
    }

    /**
     * 检查集合中是否包含某个元素
     * @param key
     * @param value
     * @return
     */
    public boolean sHasMember(String key, Object value){
        return redisTemplate.opsForSet().isMember(key, value);
    }


    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time){
        return expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 指定缓存失效时间
     * @param key key 键
     * @param time 时间
     * @param timeUnit 时间类型
     * @return
     */
    public boolean expire(String key, long time, TimeUnit timeUnit){
        if(time>0){
            redisTemplate.expire(key, time, timeUnit);
        }
        return true;
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @param timeUnit 时间类型
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key, TimeUnit timeUnit){
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 批量删除对应的value
     * @param keys 可变参数
     */
    public void delete(String... keys) {
        for (String key : keys) {
            delete(key);
        }
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void delete(String key) {
        if (hasKey(key)) {
            redisTemplate.delete(key);
        }
    }
}