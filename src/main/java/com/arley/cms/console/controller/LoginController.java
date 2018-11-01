package com.arley.cms.console.controller;

import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.exception.CustomException;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.pojo.vo.SysUserVO;
import com.arley.cms.console.service.LoginLogService;
import com.arley.cms.console.service.SysUserService;
import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.DateUtils;
import com.arley.cms.console.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XueXianlei
 * @Description: 登录
 * @date 2018/11/1 18:11
 */
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LoginLogService loginLogService;


    /**
     * 登录
     * @param userName
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public AnswerBody login(String userName, String password, HttpServletRequest request) {

        SysUserVO sysUserVO = sysUserService.getSysUserByUserName(userName);
        if (null == sysUserVO) {
            throw new CustomException(PublicCodeEnum.PARAM_ERROR.getCode(), "用户名或密码错误", CustomException.LOGGER_WARN_TYPE);
        }

        AnswerBody body = AnswerBody.buildAnswerBody();

        // 保存登录日志
        LoginLogVO loginLog = new LoginLogVO();
        loginLog.setUserName(userName);
        loginLog.setLoginTime(DateUtils.getLocalDateTime());
        loginLog.setLoginIp(RequestUtils.getClientIpAddr(request));
        loginLog.setContent("登录成功");
        loginLogService.insertLoginLog(loginLog);
        return body;
    }

}
