package com.arley.cms.console.controller;

import com.arley.cms.console.component.RedisDao;
import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.constant.PublicConstants;
import com.arley.cms.console.exception.CustomException;
import com.arley.cms.console.pojo.vo.AdminTokenVO;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.pojo.vo.SysUserVO;
import com.arley.cms.console.service.LoginLogService;
import com.arley.cms.console.service.SysUserService;
import com.arley.cms.console.shiro.Encrypt;
import com.arley.cms.console.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author XueXianlei
 * @Description: 登录
 * @date 2018/11/1 18:11
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private RedisDao redisDao;

    @RequestMapping(value = "/getUserInfo")
    public AnswerBody getUserInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("roles", new String[]{"admin"});
        data.put("introduction", "我是超级管理员");
        data.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("userName", "admin");
        AnswerBody body = AnswerBody.buildAnswerBody(data);
        return body;
    }

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

        // 校验密码
        String md5Pwd = Encrypt.md5(password, userName);
        if (!Objects.equals(md5Pwd, sysUserVO.getPassword())) {
            throw new CustomException(PublicCodeEnum.PARAM_ERROR.getCode(), "用户名或密码错误", CustomException.LOGGER_WARN_TYPE);
        }

        if (0 == sysUserVO.getUserState()) {
            throw new CustomException(PublicCodeEnum.FAIL.getCode(), "账号被禁用,请联系管理员!", CustomException.LOGGER_WARN_TYPE);
        }

        // 保存登录日志
        LoginLogVO loginLog = new LoginLogVO();
        loginLog.setUserName(userName);
        loginLog.setLoginTime(DateUtils.getLocalDateTime());
        loginLog.setLoginIp(RequestUtils.getClientIpAddr(request));
        loginLog.setContent("登录成功");
        loginLogService.insertLoginLog(loginLog);

        // 生成token
        Map<String, String> data = new HashMap<>();
        AdminTokenVO adminTokenVO = new AdminTokenVO();
        adminTokenVO.setUserName(sysUserVO.getUserName());
        adminTokenVO.setUserId(sysUserVO.getUserId());
        adminTokenVO.setName(sysUserVO.getName());
        String token = JJWTUtils.createJWT(FastJsonUtils.obj2Str(adminTokenVO), -1);
        data.put("token", token);

        String appUserTokenKey = RedisKeyUtils.getAppUserTokenKey(adminTokenVO.getUserName());
        redisDao.set(appUserTokenKey, token, PublicConstants.TOKEN_VALID_TIME, TimeUnit.MINUTES);

        return AnswerBody.buildAnswerBody(data);
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "/logout")
    public AnswerBody logout(@RequestHeader String loginUserName) {
        // 删除redis中的token
        redisDao.delete(RedisKeyUtils.getAppUserTokenKey(loginUserName));
        return AnswerBody.buildAnswerBody();
    }

    /**
     * 401
     * @return
     */
    @RequestMapping(value = "/401")
    public AnswerBody unauthorized(String resultCode, String resultDesc) {
        return AnswerBody.buildAnswerBody(resultCode, resultDesc);
    }

}
