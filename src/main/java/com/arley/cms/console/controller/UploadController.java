package com.arley.cms.console.controller;

import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.PicUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XueXianlei
 * @Description: 上传文件控制层
 * @date 2018/9/11 22:36
 */
@Controller
@RequestMapping("/api/upload")
public class UploadController {

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadPic")
    @ResponseBody
    public AnswerBody uploadPic(MultipartFile file) {
        if (file == null || file.getSize() <= 0) {
            return AnswerBody.buildAnswerBody("0001", "上传失败");
        }
        String picUrl = PicUtils.uploadPic(file);
        Map<String, String> result = new HashMap<>(1);
        result.put("picUrl", picUrl);
        return AnswerBody.buildAnswerBody(result);
    }

}
