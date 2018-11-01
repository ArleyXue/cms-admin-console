package com.arley.cms.console.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author XueXianlei
 * @Description: 初始化启动类
 * @date 2018/9/11 14:57
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    @Autowired
    private ReadResource readResource;

    @Override
    public void run(ApplicationArguments args) {

        // 加载配置资源
        readResource.flushResource();


    }


}
