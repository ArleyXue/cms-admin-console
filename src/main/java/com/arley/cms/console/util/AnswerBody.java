package com.arley.cms.console.util;


import com.alibaba.fastjson.annotation.JSONField;
import com.arley.cms.console.constant.Code;
import com.arley.cms.console.constant.PublicCodeEnum;

import java.util.List;


/**
 * @author XueXianlei
 * @Description:
 * @date Created in 2018/4/8 15:11
 */
public class AnswerBody {

    /**
     * 返回结果代号
     */
    @JSONField(ordinal = 1)
    private String resultCode;

    /**
     * 返回结果描述
     */
    @JSONField(ordinal = 2)
    private String resultDesc;

    /**
     * 返回结果数据
     */
    @JSONField(ordinal = 3)
    private Object resultData;

    private AnswerBody(String resultCode, String resultDesc) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
        this.resultData = new Object();
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "AnswerBody{" +
                "resultCode='" + resultCode + '\'' +
                ", resultDesc='" + resultDesc + '\'' +
                ", resultData=" + resultData +
                '}';
    }

    /**
     * 获得成功code的body
     *
     * @return AnswerBody
     */
    public static AnswerBody buildAnswerBody() {
        return buildAnswerBody(PublicCodeEnum.SUCCESS);
    }

    /**
     * 获得成功code的body 设置data
     *
     * @param data 数据
     * @return AnswerBody
     */
    public static AnswerBody buildAnswerBody(Object data) {
        AnswerBody body = buildAnswerBody();
        body.setResultData(data);
        return body;
    }

    /**
     * 获得指定错误code的body
     *
     * @param code code接口
     * @return AnswerBody
     */
    public static AnswerBody buildAnswerBody(Code code) {
        return buildAnswerBody(code.getCode(), code.getMsg());
    }

    /**
     * 获得指定code的body
     *
     * @param resultCode code
     * @param resultDesc msg
     * @return AnswerBody
     */
    public static AnswerBody buildAnswerBody(String resultCode, String resultDesc) {
        return new AnswerBody(resultCode, resultDesc);
    }

    /**
     * 根据集合是否为空返回特定数据
     *
     * @param data List类型数据
     * @return
     */
    public static AnswerBody buildAnswerBodyByList(List data) {
        if (CommonUtils.isNotEmptyCollection(data)) {
            return buildAnswerBody(data);
        } else {
            return buildAnswerBody(PublicCodeEnum.SUCCESS.getCode(), "暂无数据");
        }
    }
}
