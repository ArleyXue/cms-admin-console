package com.arley.cms.console.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author XueXianlei
 * @Description: 角色菜单中间表实体类
 * @date 2018/7/23 17:39
 */
@TableName("sys_role_permission")
public class SysRolePermissionDO {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer rpId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 菜单id
     */
    private Integer permissionId;

    public Integer getRpId() {
        return rpId;
    }

    public void setRpId(Integer rpId) {
        this.rpId = rpId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "SysRolePermissionDO{" +
                "rpId=" + rpId +
                ", roleId=" + roleId +
                ", permissionId=" + permissionId +
                '}';
    }
}