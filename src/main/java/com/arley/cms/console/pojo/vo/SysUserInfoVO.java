package com.arley.cms.console.pojo.vo;

import java.util.List;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/11/9 16:57
 */
public class SysUserInfoVO {

    /**
     * 用户信息
     */
    private SysUserVO sysUser;

    /**
     * 所拥有的菜单
     */
    private List<SysPermissionVO> permissionList;

    public SysUserVO getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUserVO sysUser) {
        this.sysUser = sysUser;
    }


    public List<SysPermissionVO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermissionVO> permissionList) {
        this.permissionList = permissionList;
    }
}
