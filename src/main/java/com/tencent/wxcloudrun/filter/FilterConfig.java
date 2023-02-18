package com.tencent.wxcloudrun.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * filter过滤器配置类
 *
 * @Author：zhoutao
 * @Date：2023/1/26 11:52
 */
@Configuration
@Slf4j
public class FilterConfig {

    @Bean
    public FilterRegistrationBean regiterFilterRegister() {
        FilterRegistrationBean<RegisterCheckFilter> registration = new FilterRegistrationBean();
        //注入过滤器
        registration.setFilter(new RegisterCheckFilter());
        //拦截规则
        registration.addUrlPatterns("/api/*");
        //过滤器名称
        registration.setName("registerCheckFilter");
        //过滤器顺序
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        log.warn("regiterFilterRegister init");
        return registration;
    }

}
