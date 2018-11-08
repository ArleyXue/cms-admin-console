package com.arley.cms.console.pojo.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 菜单封装类
 * @author XueXianlei
 * @date Created in 2018/2/7 0:06
 */
public class TreeVO implements Serializable {

    private Integer id;

    private String label;

    private Integer parentId;

    private Boolean disabled;

    private List<TreeVO> children;

    public TreeVO(Integer id, String label, Integer parentId, Boolean disabled) {
        this.id = id;
        this.label = label;
        this.parentId = parentId;
        this.disabled = disabled;
        this.children = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public List<TreeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVO> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeVO treeVO = (TreeVO) o;
        return Objects.equals(id, treeVO.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "XTreeVO{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", parentId=" + parentId +
                ", disabled=" + disabled +
                ", children=" + children +
                '}';
    }
}
