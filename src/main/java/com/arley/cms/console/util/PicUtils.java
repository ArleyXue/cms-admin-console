package com.arley.cms.console.util;

import com.arley.cms.console.constant.ResourceConstants;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.temporal.ChronoField;
import java.util.UUID;

/**
 * @author XueXianlei
 * @Description:
 * @date Created in 2018/5/23 16:44
 */
public class PicUtils {

    private static final String PIC_POSTFIX = "jpg";

    /**
     * 保存图片
     * @param pictureFile
     * @return
     */
    public static String uploadPic(MultipartFile pictureFile){
        String filename = pictureFile.getOriginalFilename();
        String prefix = filename.substring(filename.lastIndexOf(".")+1);
        try {

            String savePath = getSavePath();

            String picUrl = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + prefix;
            //上传到的位置
            String picPath = ResourceConstants.pic_save_path + "/" + savePath + "/" + picUrl;
            File file = new File(ResourceConstants.pic_save_path + "/" + savePath);
            if(!file.exists()){
                file.mkdirs();
            }
            //保存到指定位置
            pictureFile.transferTo(new File(file,  "/" + picUrl));

            return ResourceConstants.visit_resource_path + "/" + savePath + "/" + picUrl;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 保存base64 图片
     * @param imgStr
     * @return
     */
    public static String saveBase64Pic(String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) {
            //图像数据为空
            return null;
        }

        OutputStream out = null;
        try {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }

            //生成jpeg图片
            String picUrl = UUID.randomUUID().toString().replace("-", "").toLowerCase() + "." + PIC_POSTFIX;
            String savePath = getSavePath();
            String picPath = ResourceConstants.pic_save_path + "/" + savePath + "/" + picUrl;
            File file = new File(ResourceConstants.pic_save_path + "/" + savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            out = new FileOutputStream(picPath);
            out.write(b);
            out.flush();
            return ResourceConstants.visit_resource_path + "/" + savePath + "/" + picUrl;
        } catch (Exception e) {
            return null;
        } finally {
            if(null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取保存地址
     * @return
     */
    private static String getSavePath() {
        String yy = DateUtils.formatLocalDate("yy");
        String mm = DateUtils.formatLocalDate("MM");
        //第几周
        String week = "0" + DateUtils.getLocalDate().get(ChronoField.ALIGNED_WEEK_OF_MONTH);
        return yy + "/" + mm + "/" +week;
    }


}
