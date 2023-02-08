package com.tencent.wxcloudrun.common;

import lombok.Data;

/**
 * 通用的登陆上下文
 *
 * @Author：zhoutao
 * @Date：2023/1/26 11:56
 */
@Data
public class LoginContext {

    public static ThreadLocal<String> holder = new ThreadLocal<>();

    private String openId;  // 微信openId

    public static void createLoginContext(String openId) {
        holder.set(openId);
    }

    public static String getOpenId() {
        return holder.get();
    }

    /**
     * 销毁threadlocal
     */
    public static void destoryLoginContext() {
        holder.remove();
    }

}
