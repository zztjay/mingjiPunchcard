package com.tencent.wxcloudrun.filter;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆态检查过滤器
 * @Author：zhoutao
 * @Date：2023/1/26 11:53
 */

@Slf4j
public class RegisterCheckFilter implements Filter {

    public static final String OPENID = "X-WX-OPENID";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String openId = httpRequest.getHeader(OPENID);

        log.warn("RegisterCheckFilter, openId:{}",openId);

        // 用户未授权注册，返回用户授权注册
        ServletContext servletContext = servletRequest.getServletContext();
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        UserService userService =  applicationContext.getBean(UserService.class);
        if(!userService.isUserRegister(openId)){
            HttpServletResponse  httpServletResponse = (HttpServletResponse) servletResponse;
            ApiResponse response =  ApiResponse.error("USER_NOT_REGISTER", "用户未注册");
            httpServletResponse.getWriter().print(JSON.toJSONString(response));
            log.warn("RegisterCheckFilter, not register, openId:{}",openId);
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

}
