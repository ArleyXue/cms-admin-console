package com.arley.cms.console.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author XueXianlei
 * @Description: json工具类
 * @date 2018/9/6 14:47
 */
public class FastJsonUtils {


    /**
     * 对象转str
     * @param obj
     * @return
     */
    public static String obj2Str(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
    }

    /**
     * json字符串转map集合
     * @param jsonStr
     * @return
     */
    public static Map json2Map(String jsonStr){
        return JSONObject.parseObject(jsonStr);
    }

    /**
     * json字符串转换成对象
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T json2Bean(String jsonStr, Class<T> clazz){
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * 转换为List
     * @param text
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

}
