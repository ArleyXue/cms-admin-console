package com.arley.cms.console.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author XueXianlei
 * @Description: 用户角色中间表实体类
 * @date 2018/7/23 17:39
 */
@TableName("sys_user_role")
public class SysUserRoleDO {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer urId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 角色id
     */
    private Integer roleId;

    public Integer getUrId() {
        return urId;
    }

    public void setUrId(Integer urId) {
        this.urId = urId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysUserRoleDO{" +
                "urId=" + urId +
                ", userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}