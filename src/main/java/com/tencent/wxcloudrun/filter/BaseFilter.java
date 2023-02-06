package com.tencent.wxcloudrun.filter;

import com.tencent.wxcloudrun.common.LoginContext;
import org.springframework.util.StringUtils;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 基础的过滤器，增加登陆态等信息
 * @Author：zhoutao
 * @Date：2023/1/26 11:53
 */

public class BaseFilter implements Filter {
    public static final String OPENID = "X-WX-OPENID";
    public static final String UERID = "UER_ID";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // 设置登陆上下文
        String userId = httpRequest.getHeader(UERID);
        if(!StringUtils.isEmpty(userId)){
            LoginContext.createLoginContext(httpRequest.getHeader(OPENID));
        }
    }

    @Override
    public void destroy() {
        LoginContext.destoryLoginContext();
    }
}
