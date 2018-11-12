package com.arley.cms.console.service;

import com.arley.cms.console.pojo.query.LoginLogQuery;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.util.Pagination;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
public interface LoginLogService {

    /**
     * 添加登录日志
     * @param loginLog
     */
    void insertLoginLog(LoginLogVO loginLog);

    /**
     * 分页查询登录日志
     * @param loginLogQuery
     * @return
     */
    Pagination listLoginLogByPage(LoginLogQuery loginLogQuery);

    /**
     * 查询一周的访问量
     * @return
     */
    List<LinkedHashMap<String, Integer>> listPageViewByWeek();

    /**
     * 查询一周的访问用户
     * @return
     */
    List<LinkedHashMap<String,Integer>> listUserViewByWeek();

    /**
     * 获取用户上次登录日志
     * @param userName
     * @return
     */
    LoginLogVO getLastLoginLog(String userName);

}
