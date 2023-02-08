package com.tencent.wxcloudrun.filter;

import com.tencent.wxcloudrun.common.LoginContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 基础的过滤器，增加登陆态等信息
 * @Author：zhoutao
 * @Date：2023/1/26 11:53
 */
@Slf4j
public class BaseFilter implements Filter {
    public static final String OPENID = "X-WX-OPENID";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       log.warn("init BaseFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // 设置登陆上下文
        LoginContext.createLoginContext(httpRequest.getHeader(OPENID));

        log.warn("BaseFilter, not register, openId:{}",LoginContext.getOpenId());
    }

    @Override
    public void destroy() {
        LoginContext.destoryLoginContext();
    }
}
