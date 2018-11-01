package com.arley.cms.console.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.arley.cms.console.interceptor.ApiLogFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author XueXianlei
 * @Description: 拦截器配置
 * @date 2018/9/13 18:16
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 入口过滤器注册
     * @return
     */
    @Bean
    public FilterRegistrationBean apiLogFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(apiLogFilter());
        // 拦截路径
        registration.addUrlPatterns("/*");
        // 拦截器名称
        registration.setName("apiLogFilter");
        // 顺序
        registration.setOrder(1);
        // 设置不拦截的路径
        registration.addInitParameter("excludeUrlPatterns", "/static/**");
        return registration;
    }



    /**
     * 入口过滤器
     * @return
     */
    @Bean
    public ApiLogFilter apiLogFilter() {
        return new ApiLogFilter();
    }

    /**
     * 使用fastJson转换json
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonConfigure(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        //日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter);
    }

    @Bean
    public Converter<String, String> stringConvert() {
        return new Converter<String, String>() {
            @Override
            public String convert(String source) {
                return StringUtils.trimToNull(source);
            }
        };
    }

    @Bean
    public Converter<String, LocalDate> localDateConvert() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

        };
    }

    @Bean
    public Converter<String, Date> dateConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                if(StringUtils.isBlank(source)){
                    try {
                        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        return format.parse(source);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
    }

    @Bean
    public Converter<String, LocalDateTime> localDateTimeConvert() {
        return new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                if (StringUtils.isBlank(source)) {
                    return null;
                }
                return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

        };
    }

}
