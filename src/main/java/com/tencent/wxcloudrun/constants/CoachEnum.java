package com.tencent.wxcloudrun.constants;

import lombok.Data;

/**
 * @Author：zhoutao
 * @Date：2023/1/29 16:39
 */

public enum CoachEnum {
    guohui("1","国晖"),
    yinshao("2","英少"),
    taohe("oOPIl41B88kz0n-1mscSTsH3fJeM","周韬"),
    shicong("oOPIl45BU7yfmV-0bYYKX-Os64G0","邱诗翀");
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

    public static boolean isCoach(String openId){
        return getByOpenId(openId) != null;
    }
}
