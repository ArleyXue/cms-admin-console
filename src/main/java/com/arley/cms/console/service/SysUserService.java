package com.arley.cms.console.service;

import com.arley.cms.console.pojo.vo.SysUserVO;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
public interface SysUserService {

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    SysUserVO getSysUserByUserName(String userName);
}
