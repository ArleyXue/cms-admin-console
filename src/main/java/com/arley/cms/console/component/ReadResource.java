package com.arley.cms.console.component;

import com.arley.cms.console.constant.ResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author XueXianlei
 * @Description: 读取资源文件类
 * @date Created in 2018/4/17 15:54
 */
@Component
public class ReadResource {
    private static final Logger logger = LoggerFactory.getLogger(ReadResource.class);
    /**
     * 刷新资源
     * @return
     */
    public Map<String, Object> flushResource() {
        ResourceBundle.clearCache();
        ResourceBundle rb = ResourceBundle.getBundle("config/config");
        Map<String, Object> resource = new LinkedHashMap<>();

        // 使用反射刷新资源
        Class clazz = ResourceConstants.class;
        Field[] fields = clazz.getFields();
        for( Field field : fields ){
            if (Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                String name = field.getName();
                try {
                    Class<?> type = field.getType();
                    String value = rb.getString(name);
                    field.set(null, getVal(value, type));
                    resource.put(name, field.get(name));
                } catch (IllegalAccessException e) {
                    logger.error("加载资源失败");
                }
            }
        }

        logger.info("----加载成功的配置资源-------");
        resource.forEach((key, value) -> {
            logger.info("{}={}", key, value);
        });
        return resource;
    }


    private Object getVal(String str, Class type) {
        if (type == String.class) {
            return str;
        }
        if (type == Integer.class) {
            return Integer.parseInt(str);
        }
        return null;
    }
}
