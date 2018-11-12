package com.arley.cms.console.mapper;


import com.arley.cms.console.pojo.Do.LoginLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.LinkedHashMap;
import java.util.List;


public interface LoginLogMapper extends BaseMapper<LoginLogDO> {

    /**
     * 查询一周的访问量
     * @return
     */
    List<LinkedHashMap<String,Integer>> listPageViewByWeek();

    /**
     * 查询一周的访问用户
     * @return
     */
    List<LinkedHashMap<String,Integer>> listUserViewByWeek();

    /**
     * 获取用户上次登录的日志
     * @param userName
     * @return
     */
    LoginLogDO getLastLoginLog(String userName);
}