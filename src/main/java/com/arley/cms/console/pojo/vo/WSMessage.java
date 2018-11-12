package com.arley.cms.console.pojo.vo;

/**
 * @author XueXianlei
 * @Description: ws消息
 * @date 2018/11/12 11:26
 */
public class WSMessage {

    /**
     * 目标id
     */
    private String sid;

    /**
     * 类型 1-登录登出日志
     */
    private Integer type;

    /**
     * 消息
     */
    private Object message;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "WSMessage{" +
                "sid='" + sid + '\'' +
                ", type=" + type +
                ", message=" + message +
                '}';
    }
}
