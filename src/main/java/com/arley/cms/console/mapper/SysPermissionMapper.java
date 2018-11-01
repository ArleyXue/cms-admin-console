package com.arley.cms.console.mapper;


import com.arley.cms.console.pojo.Do.SysPermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface SysPermissionMapper extends BaseMapper<SysPermissionDO> {

    /**
     * 获取用户拥有的权限
     * @param userId
     * @return
     */
    List<SysPermissionDO> listHavePermission(Integer userId);

    /**
     * 获取所有权限 层级
     * @return
     */
    List<SysPermissionDO> listHaveHierarchyPermission();

    /**
     * 获取用户拥有的权限 层级
     * @param
     * @return
     */
    List<SysPermissionDO> listPermissionByParentId();

    /**
     * 根据角色ID查看权限
     * @param roleId
     * @return
     */
    List<SysPermissionDO> listPermissionByRoleId(Integer roleId);
}