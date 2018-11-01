package com.arley.cms.console.mapper;


import com.arley.cms.console.pojo.Do.SysRoleDO;
import com.arley.cms.console.pojo.Do.SysRolePermissionDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    /**
     * 删除角色菜单
     * @param roleId
     */
    void deleteRolePermissionByRoleId(Integer roleId);

    /**
     * 保存角色菜单
     * @param rolePermissionList
     */
    void saveRolePermission(List<SysRolePermissionDO> rolePermissionList);

    /**
     * 获取管理员拥有的角色
     * @param userId
     * @return
     */
    SysRoleDO getRoleBySysUserId(Integer userId);
}