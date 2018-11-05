package com.arley.cms.console.util;

import com.arley.cms.console.pojo.query.PageQuery;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/9/30 13:36
 */
public class PageUtils {

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    /**
     * 指定排序
     * @param page
     * @param pageQuery
     * @param clazz
     * @return
     */
    public static boolean pageAndOrderBy(Page page, PageQuery pageQuery, Class clazz) {
        page.setCurrent(pageQuery.getPage()).setSize(pageQuery.getLimit());
        if (StringUtils.isBlank(pageQuery.getSortField()) && StringUtils.isBlank(pageQuery.getSortOrder())) {
            return false;
        }
        if (Objects.equals(pageQuery.getSortOrder(), "ascending")) {
            page.setAsc(getUnderscoreToCamelCase(pageQuery.getSortField(), clazz));
        } else {
            page.setDesc(getUnderscoreToCamelCase(pageQuery.getSortField(), clazz));
        }
        return true;
    }

    /**
     * 驼峰转下划线
     * @param str
     * @param clazz
     * @return
     */
    public static String getUnderscoreToCamelCase(String str, Class clazz) {
        // 首先查询是否注解改变了字段值
        try {
            Field field =  clazz.getDeclaredField(str);
            TableField annotation = field.getAnnotation(TableField.class);
            if (null != annotation && StringUtils.isNotBlank(annotation.value())) {
                return annotation.value();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        // 驼峰转换
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}


