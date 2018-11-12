package com.arley.cms.console.controller;

import com.arley.cms.console.util.AnswerBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XueXianlei
 * @Description:
 * @date 2018/11/12 14:02
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    /**
     * 获取内存使用情况
     * @return
     */
    @RequestMapping(value = "/listMemoryData")
    public AnswerBody listMemoryData() {
        // Java虚拟机中的内存总量
        Long totalMemory = Runtime.getRuntime().totalMemory();
        // Java虚拟机中的可用内存量
        Long freeMemory = Runtime.getRuntime().freeMemory();
        Long useMemory = totalMemory - freeMemory;
        Double useMemoryPercent = useMemory.doubleValue() / totalMemory.doubleValue();
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(useMemoryPercent);
        Map<String, Object> result = new HashMap<>();
        result.put("useMemoryPercent", format);
        return AnswerBody.buildAnswerBody(result);
    }

}
