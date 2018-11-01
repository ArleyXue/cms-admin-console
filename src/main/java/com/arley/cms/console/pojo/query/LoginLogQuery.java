package com.arley.cms.console.pojo.query;

/**
 * @author XueXianlei
 * @Description: 登录日志query
 * @date 2018/9/30 11:09
 */
public class LoginLogQuery extends PageQuery {

    private Integer type;

    private String userName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
