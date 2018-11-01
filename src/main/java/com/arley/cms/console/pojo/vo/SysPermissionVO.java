package com.arley.cms.console.pojo.vo;

import java.util.List;

/**
 * @author XueXianlei
 * @Description: 菜单VO
 * @date 2018/7/23 17:39
 */
public class SysPermissionVO {
    /**
     * 主键ID
     */
    private Integer permissionId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单父Id
     */
    private Integer parentId;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 菜单code
     */
    private String menuCode;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 排序字段
     */
    private Integer menuPriority;

    /**
     * 状态 1-开启 0-关闭
     */
    private Integer menuState;

    /**
     * 菜单类型 1-菜单 2-按钮
     */
    private Integer menuType;

    /**
     * 修改人
     */
    private String modifier;

    private List<SysPermissionVO> permissionList;

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon == null ? null : menuIcon.trim();
    }

    public Integer getMenuPriority() {
        return menuPriority;
    }

    public void setMenuPriority(Integer menuPriority) {
        this.menuPriority = menuPriority;
    }

    public Integer getMenuState() {
        return menuState;
    }

    public void setMenuState(Integer menuState) {
        this.menuState = menuState;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public List<SysPermissionVO> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermissionVO> permissionList) {
        this.permissionList = permissionList;
    }

    @Override
    public String toString() {
        return "SysPermissionVO{" +
                "permissionId=" + permissionId +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                ", menuUrl='" + menuUrl + '\'' +
                ", menuCode='" + menuCode + '\'' +
                ", menuIcon='" + menuIcon + '\'' +
                ", menuPriority=" + menuPriority +
                ", menuState=" + menuState +
                ", menuType=" + menuType +
                ", modifier='" + modifier + '\'' +
                ", permissionList=" + permissionList +
                '}';
    }
}