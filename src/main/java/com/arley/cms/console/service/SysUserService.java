package com.arley.cms.console.service;

import com.arley.cms.console.pojo.query.SysUserQuery;
import com.arley.cms.console.pojo.vo.SysUserVO;
import com.arley.cms.console.util.Pagination;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
public interface SysUserService {

    /**
     * 分页查询系统用户
     * @param sysUserQuery
     * @return
     */
    Pagination listSysUserByPage(SysUserQuery sysUserQuery);

    /**
     * 添加管理员
     * @param sysUserVO
     * @param roleId
     */
    void insertSysUser(SysUserVO sysUserVO, Integer roleId);

    /**
     * 根据ID获取管理员
     * @param userId
     * @return
     */
    SysUserVO getSysUser(Integer userId);

    /**
     * 删除管理员
     * @param userId
     */
    void deleteSysUser(Integer userId);

    /**
     * 检查用户名是否存在
     * @param userName
     * @return
     */
    boolean checkIsExistUserName(String userName);

    /**
     * 修改管理员
     * @param sysUserVO
     * @param roleId
     * @param resetPassword
     */
    void updateSysUser(SysUserVO sysUserVO, Integer roleId, Integer resetPassword);

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @param userId
     */
    void updatePassword(String oldPassword, String newPassword, Integer userId);

    /**
     * 修改个人信息
     * @param sysUserVO
     */
    void updateUserInfo(SysUserVO sysUserVO);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    SysUserVO getSysUserByUserName(String username);

    /**
     * 登陆后进行更新
     * @param userId
     */
    void updateForLogin(Integer userId);

}
