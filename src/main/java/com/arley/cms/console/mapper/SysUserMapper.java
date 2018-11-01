package com.arley.cms.console.mapper;


import com.arley.cms.console.pojo.Do.SysUserDO;
import com.arley.cms.console.pojo.Do.SysUserRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


public interface SysUserMapper extends BaseMapper<SysUserDO> {

    /**
     * 保存管理员权限中间表
     * @param sysUserRoleDO
     */
    void insertSysUserRole(SysUserRoleDO sysUserRoleDO);

    /**
     * 删除管理员权限中间表
     * @param sysUserRoleDO
     */
    void deleteSysUserRole(SysUserRoleDO sysUserRoleDO);
}