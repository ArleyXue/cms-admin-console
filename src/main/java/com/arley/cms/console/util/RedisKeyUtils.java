package com.arley.cms.console.util;


import com.arley.cms.console.constant.RedisKeyConstants;

/**
 * @author XueXianlei
 * @Description: redis key 工具类
 * @date 2018/9/10 11:57
 */
public class RedisKeyUtils {


    /**
     * 获取admin用户token key
     * @param userName
     * @return
     */
    public static String getAppUserTokenKey(String userName) {
        return RedisKeyConstants.ADMIN_USER_TOKEN_KEY + userName;
    }


}
