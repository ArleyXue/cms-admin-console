package com.arley.cms.console.constant;

/**
 * @author XueXianlei
 * @Description: code码
 * @date 2018/8/16 17:29
 */
public enum PublicCodeEnum implements Code {
    // 成功
    SUCCESS("0000", "success"),
    FAIL("0001", "操作失败!"),
    PARAM_EMPTY("0002", "参数为空!"),
    PARAM_ERROR("0003", "参数错误!"),
    TOKEN_VERIFY_FAIL("1000", "登录已失效，请重新登录!"),
    SERVICE_NOT_EXIST("9000", "服务不存在!"),
    ERROR("9999", "服务异常, 请稍后重试!");


    private String code;
    private String msg;

    PublicCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
