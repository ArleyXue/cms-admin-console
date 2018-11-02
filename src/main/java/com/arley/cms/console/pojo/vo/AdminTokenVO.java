package com.arley.cms.console.pojo.vo;

/**
 * @author XueXianlei
 * @Description: 系统用户tokenVO
 * @date 2018/11/2 11:23
 */
public class AdminTokenVO {

    /**
     * 主键ID
     */
    private Integer userId;

    private String userName;

    private String name;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
