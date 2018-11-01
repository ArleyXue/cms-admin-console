package com.arley.cms.console.exception;

/**
 * @author XueXianlei
 * @Description: 自定义异常类
 * @date Created in 2018/4/4 15:48
 */
public class CustomException extends RuntimeException {

    public static final Integer NOT_WRITE_LOGGER = 0;
    public static final Integer LOGGER_INFO_TYPE = 1;
    public static final Integer LOGGER_WARN_TYPE = 2;
    public static final Integer LOGGER_ERROR_TYPE = 3;

    private String code;

    private String msg;

    private Integer type;

    public CustomException(String code, String msg, Integer type) {
        super(code + " --- " + msg);
        this.code = code;
        this.msg = msg;

        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
