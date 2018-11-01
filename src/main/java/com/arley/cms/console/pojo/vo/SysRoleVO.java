package com.arley.cms.console.pojo.vo;

/**
 * @author XueXianlei
 * @Description: 角色VO
 * @date 2018/7/23 17:39
 */

public class SysRoleVO {
    /**
     * 主键id
     */
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

}