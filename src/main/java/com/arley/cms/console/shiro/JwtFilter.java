package com.arley.cms.console.shiro;

import com.arley.cms.console.constant.PublicCodeEnum;
import com.arley.cms.console.constant.PublicConstants;
import com.arley.cms.console.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author Mr.Li
 * @create 2018-07-12 15:56
 * @desc
 **/
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(PublicConstants.REQUEST_HEADER_TOKEN_NAME);
        return authorization != null;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(PublicConstants.REQUEST_HEADER_TOKEN_NAME);
        //判断请求的请求头是否带上 "Token"
        if (StringUtils.isNotBlank(token)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                executeLogin(request, response);
            } catch (ExpiredCredentialsException e1) {
                response401(request, response, PublicCodeEnum.TOKEN_NOT_EXIST.getCode(), PublicCodeEnum.TOKEN_NOT_EXIST.getMsg());
            } catch (IncorrectCredentialsException e2) {
                response401(request, response, PublicCodeEnum.TOKEN_VERIFY_FAIL.getCode(), PublicCodeEnum.TOKEN_VERIFY_FAIL.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }
 
    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader(PublicConstants.REQUEST_HEADER_TOKEN_NAME);;
        JWTToken jwtToken = new JWTToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response401(ServletRequest request, ServletResponse resp, String resultCode, String resultDesc) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
            String contextPath = httpServletRequest.getContextPath();
            httpServletResponse.sendRedirect(contextPath + "/api/user/401?resultCode=" + resultCode + "&resultDesc=" + URLEncoder.encode(resultDesc, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}