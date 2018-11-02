package com.arley.cms.console.exception;

import com.alibaba.fastjson.JSON;
import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.util.AnswerBody;
import com.arley.cms.console.util.RequestUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author XueXianlei
 * @Description: 异常处理
 * @date Created in 2018/4/8 15:14
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    /**
     * 系统异常
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public AnswerBody exception(Exception exception, HttpServletRequest request){
        logger.error(exception.getMessage(), exception);
        AnswerBody body = AnswerBody.buildAnswerBody(PublicCodeEnum.ERROR.getCode(), PublicCodeEnum.ERROR.getMsg());
        logger.error("【系统异常】异常信息={} | 请求路径={} | 请求IP={} | 请求参数={} | 响应内容={}",
                exception.getMessage(),
                request.getRequestURI(),
                RequestUtils.getClientIpAddr(request),
                RequestUtils.readRequestParamsJSONString(request),
                JSON.toJSONString(body));
        return body;
    }

    /**
     * 自定义异常
     * @param exception
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public AnswerBody customException(CustomException exception){
        AnswerBody body = AnswerBody.buildAnswerBody(exception.getCode(), exception.getMsg());
        if (CustomException.LOGGER_INFO_TYPE.equals(exception.getType())) {
            logger.info("【自定义异常】异常信息={}",
                    exception.getMessage());
        } else if (CustomException.LOGGER_WARN_TYPE.equals(exception.getType())) {
            logger.warn("【自定义异常】异常信息={}",
                    exception.getMessage());
        } else if (CustomException.LOGGER_ERROR_TYPE.equals(exception.getType())) {
            logger.error("【自定义异常】异常信息={}",
                    exception.getMessage());
        }
        return body;
    }

    /**
     * shiro无权限异常
     * @return
     */
    @ExceptionHandler(ShiroException.class)
    public AnswerBody unauthenticated() {
        logger.error("没有访问权限");
        return AnswerBody.buildAnswerBody(PublicCodeEnum.NO_PERMISSION);
    }
}
