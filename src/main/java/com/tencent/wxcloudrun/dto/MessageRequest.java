package com.tencent.wxcloudrun.dto;

import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.constants.CommonConstants;
import com.tencent.wxcloudrun.constants.MsgArgumentEnum;
import lombok.Data;

/**
 * 订阅消息发送请求
 *
 * @Author：zhoutao
 * @Date：2023/2/18 14:32
 */
@Data
public class MessageRequest {
    private static final String KEY_WORLD_VALUE = "value";
    private String template_id;    // 所需下发的订阅模板id
    private String page; // 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转
    private String touser; // 接收者（用户）的 openid
    private JSONObject data = new JSONObject(); // 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }的object
    private String miniprogram_state = "formal"; // 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
    private String lang = "zh_CN"; // 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN

    public MessageRequest addData(MsgArgumentEnum type, int typeNum, String val) {
        JSONObject value = new JSONObject();
        value.put(KEY_WORLD_VALUE, val);
        data.put(type.name() + typeNum, value);
        return this;
    }

    public static void main(String[] args) {

    }
}
