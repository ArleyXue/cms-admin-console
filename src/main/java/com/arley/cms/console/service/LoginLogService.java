package com.arley.cms.console.service;

import com.arley.cms.console.pojo.vo.LoginLogVO;

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


}
