package com.tencent.wxcloudrun.constants;

import lombok.Data;

/**
 * @Author：zhoutao
 * @Date：2023/1/29 16:39
 */

public enum CoachEnum {
    guohui("1","国晖"),
    yinshao("2","英少");
    private String openId;
    private String name;

    public String getOpenId() {
        return openId;
    }

    public String getName() {
        return name;
    }

    CoachEnum(String openId, String name) {
        this.openId = openId;
        this.name = name;
    }

    public static CoachEnum getByName(String name){
        for (CoachEnum value : CoachEnum.values()) {
            if(value.getName().equals(name)){
                return value;
            }
        }
        return null;
    }

    public static CoachEnum getByOpenId(String openId){
        for (CoachEnum value : CoachEnum.values()) {
            if(value.getOpenId().equals(openId)){
                return value;
            }
        }
        return null;
    }
}
