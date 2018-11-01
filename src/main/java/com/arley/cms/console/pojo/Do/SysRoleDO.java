package com.arley.cms.console.pojo.Do;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author XueXianlei
 * @Description: 角色实体类
 * @date 2018/7/23 17:39
 */
@TableName("sys_role")
public class SysRoleDO {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 状态 1-可用 0-不可用
     */
    private Integer roleState;

    /**
     * 备注
     */
    private String remark;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 所拥有的菜单
     */
    @TableField(exist = false)
    private List<SysPermissionDO> permissionList;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public List<SysPermissionDO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermissionDO> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "SysRoleDO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleState=" + roleState +
                ", remark='" + remark + '\'' +
                ", modifier='" + modifier + '\'' +
                ", gmtModified=" + gmtModified +
                ", gmtCreate=" + gmtCreate +
                ", permissionList=" + permissionList +
                '}';
    }
}