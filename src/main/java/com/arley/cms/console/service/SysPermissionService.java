package com.arley.cms.console.service;

import com.arley.cms.console.pojo.vo.SysPermissionVO;

import java.util.List;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/8/16 11:15
 */
public interface SysPermissionService {

    /**
     * 获取用户拥有的权限
     * @param userId
     * @return
     */
    List<SysPermissionVO> listHavePermission(Integer userId);

    /**
     * 获取所有的权限 层级
     * @return
     */
    List<SysPermissionVO> listHaveHierarchyPermission();

    /**
     * 根据角色ID查看权限
     * @param roleId
     * @return
     */
    List<SysPermissionVO> listPermissionByRoleId(Integer roleId);

    /**
     * 获取所有菜单列表
     * @return
     */
    List<SysPermissionVO> listPermission();

    /**
     * 保存菜单
     * @param sysPermissionVO
     */
    void insertPermission(SysPermissionVO sysPermissionVO);

    /**
     * 修改菜单
     * @param sysPermissionVO
     */
    void updatePermission(SysPermissionVO sysPermissionVO);

}
