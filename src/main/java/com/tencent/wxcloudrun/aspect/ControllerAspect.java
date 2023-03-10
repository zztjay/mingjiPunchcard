package com.tencent.wxcloudrun.aspect;

import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;

/**
 * @Author：zhoutao
 * @Date：2023/2/18 21:54
 */
@Aspect
@Component
@Slf4j
public class ControllerAspect {

    /**
     * 定义一个切点，后续通知方法将会使用该节点来进行获取
     * 将Controller层中的所有方法作为切面与业务逻辑交互点
     */
    @Pointcut("execution(public * com.tencent.wxcloudrun.controller.*.*(..))")
    public void controllerPointcut() {

    }


    /**
     * 环绕通知
     * 业务内容前面执行一些信息、业务内容后面再执行一些信息
     *
     * @param proceedingJoinPoint
     * @return 返回请求方法返回的结果
     */
    @Around("controllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 设置登陆态
        LoginContext.createLoginContext(request.getHeader(CommonConstants.OPENID));

        // 获取签名
        Signature signature = proceedingJoinPoint.getSignature();
        String name = signature.getName();

        // 打印请求信息
        StringBuilder logInfo = new StringBuilder("-----Aspect 开始-----").append("\n");
        logInfo.append("openUid:").append(LoginContext.getOpenId()).append("\n");
        logInfo.append("请求地址：").append(request.getRequestURL().toString()).append(",").append(request.getMethod()).append("\n");
        logInfo.append("类名方法：").append(signature.getDeclaringTypeName()).append(",").append(name).append("\n");
        try {
            // 获取返回结果
            Object result = proceedingJoinPoint.proceed();
            logInfo.append("返回结果:").append(JSONObject.toJSONString(result)).append("\n");
            return result;
        } catch (Throwable e) {
            log.error("方法调用异常", e);
            return ApiResponse.error("EXCEPTION", "系统异常");
        } finally {
            LoginContext.destoryLoginContext();
            logInfo.append("-----Aspect 结束 耗时：").append(System.currentTimeMillis() - startTime).append(" ms-----");
            log.info(logInfo.toString());
        }
    }
}
