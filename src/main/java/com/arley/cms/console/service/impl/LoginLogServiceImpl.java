package com.arley.cms.console.service.impl;

import com.arley.cms.console.mapper.LoginLogMapper;
import com.arley.cms.console.pojo.Do.LoginLogDO;
import com.arley.cms.console.pojo.vo.LoginLogVO;
import com.arley.cms.console.service.LoginLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
@Service("loginLogService")
@Transactional(rollbackFor = Exception.class)
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void insertLoginLog(LoginLogVO loginLog) {
        LoginLogDO loginLogDO = new LoginLogDO();
        BeanUtils.copyProperties(loginLog, loginLogDO);
        loginLogMapper.insert(loginLogDO);
    }

}
