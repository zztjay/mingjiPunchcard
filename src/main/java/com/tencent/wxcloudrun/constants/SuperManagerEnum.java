package com.tencent.wxcloudrun.constants;

/**
 * 超级管理员枚举
 * @Author：zhoutao
 * @Date：2023/1/29 16:39
 */

public enum SuperManagerEnum {
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

    SuperManagerEnum(String openId, String name) {
        this.openId = openId;
        this.name = name;
    }

    public static SuperManagerEnum getByName(String name){
        for (SuperManagerEnum value : SuperManagerEnum.values()) {
            if(value.getName().equals(name)){
                return value;
            }
        }
        return null;
    }

    public static SuperManagerEnum getByOpenId(String openId){
        for (SuperManagerEnum value : SuperManagerEnum.values()) {
            if(value.getOpenId().equals(openId)){
                return value;
            }
        }
        return null;
    }

    public static boolean isSuper(String openId){
        return getByOpenId(openId) != null;
    }
}
