package com.arley.cms.console.controller;

import com.arley.cms.console.pojo.query.LoginLogQuery;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.service.LoginLogService;
import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;

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

    /**
     * 获取指定数量的日志
     * @param size
     * @return
     */
    @RequestMapping(value = "/listLoginLogBySize")
    public AnswerBody listLoginLogBySize(Integer size) {
        LoginLogQuery loginLogQuery = new LoginLogQuery();
        loginLogQuery.setLimit(size);
        loginLogQuery.setPage(1);
        Pagination pagination = loginLogService.listLoginLogByPage(loginLogQuery);
        return AnswerBody.buildAnswerBody(pagination.getData());
    }

    /**
     * 查询一周的访问量
     * @return
     */
    @RequestMapping(value = "/listPageViewByWeek")
    public AnswerBody listPageViewByWeek() {
        List<LinkedHashMap<String, Integer>> result = loginLogService.listPageViewByWeek();
        return AnswerBody.buildAnswerBody(result);
    }

    /**
     * 查询一周的访问用户
     * @return
     */
    @RequestMapping(value = "/listUserViewByWeek")
    public AnswerBody listUserViewByWeek() {
        List<LinkedHashMap<String, Integer>> result = loginLogService.listUserViewByWeek();
        return AnswerBody.buildAnswerBody(result);
    }

}
