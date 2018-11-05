package com.arley.cms.console.constant;

/**
 * @author XueXianlei
 * @Description: 公共常量
 * @date 2018/9/6 14:37
 */
public class PublicConstants {


    /**
     * 超级管理员用户名
     */
    public static final String ADMIN_USER_NAME = "admin";
    /**
     * 角色(超级管理员)DI
     */
    public static final Integer SUPER_ROLE_ID = 1;

    /**
     * tokenkey
     */
    public static final String TOKEN_KEY = "arley";

    /**
     * request请求头中token的name
     */
    public static final String REQUEST_HEADER_TOKEN_NAME = "token";


    /**
     * TOKEN过期时间，以分为单位，一小时
     */
    public final static int TOKEN_VALID_TIME = 60;

    /**
     * shiro缓存时间，以分为单位，一小时
     */
    public final static int SHIRO_CACHE_TIME = 60;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";

}
