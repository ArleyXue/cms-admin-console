package com.arley.cms.console.controller;

import com.arley.cms.console.pojo.query.LoginLogQuery;
import com.arley.cms.console.service.LoginLogService;
import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XueXianlei
 * @Description: 日志控制层
 * @date 2018/9/30 11:08
 */
@RestController
@RequestMapping("/api/log")
public class LogController {

    @Autowired
    private LoginLogService loginLogService;

    /**
     * 分页查询登录日志
     * @param loginLogQuery
     * @return
     */
    @RequestMapping(value = "/listLoginLogByPage")
    public AnswerBody listLoginLogByPage(LoginLogQuery loginLogQuery, @RequestHeader String loginUserName) {
        if (null != loginLogQuery.getType()) {
            loginLogQuery.setUserName(loginUserName);
        }
        Pagination pagination = loginLogService.listLoginLogByPage(loginLogQuery);
        return AnswerBody.buildAnswerBody(pagination);
    }

}
