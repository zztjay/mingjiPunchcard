package com.tencent.wxcloudrun.constants;

/**
 * @Author：zhoutao
 * @Date：2023/1/29 16:39
 */
public enum CoachEnum {
    guohui("1","国晖"),
    yinshao("2","英少");
    private String openId;
    private String name;

    CoachEnum(String openId, String name) {
        this.openId = openId;
        this.name = name;
    }
}
