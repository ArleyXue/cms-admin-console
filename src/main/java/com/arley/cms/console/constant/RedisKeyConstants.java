package com.arley.cms.console.constant;

/**
 * @author XueXianlei
 * @Description: redis key 常量
 * @date 2018/9/10 11:04
 */
public class RedisKeyConstants {

    /**
     * redis key 前缀
     */
    private static final String REDIS_PREFIX = "CMS_";

    /**
     * redis admin用户Token key
     */
    public static final String ADMIN_USER_TOKEN_KEY = REDIS_PREFIX + "ADMIN_USER_TOKEN_";

    /**
     * redis admin用户首页数据信息 key
     */
    public static final String ADMIN_USER_INDEX_DATA_KEY = REDIS_PREFIX + "ADMIN_USER_INDEX_DATA_";



}
