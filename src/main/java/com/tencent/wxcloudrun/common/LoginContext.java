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
    private static String openId;  // 微信openId
    private static Long userId; // 用户DB的ID

    public static String getOpenId() {
        return openId;
    }

    public static void setOpenId(String requestOpenId) {
        openId = requestOpenId;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long requestUserId) {
        userId = requestUserId;
    }
}
