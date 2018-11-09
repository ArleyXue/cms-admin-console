package com.arley.cms.console.service;

import com.arley.cms.console.pojo.query.LoginLogQuery;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.util.Pagination;

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
}
