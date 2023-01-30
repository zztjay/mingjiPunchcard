package com.tencent.wxcloudrun.constants;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @Author：zhoutao
 * @Date：2023/1/29 16:39
 */

public enum TeamEnum {
    yuanli("yuanli","原力", Arrays.asList(1l,2l));
    private String teamCode;
    private String teamName;
    private List<Long> activityIds;

    public String getTeamCode() {
        return teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Long> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<Long> activityIds) {
        this.activityIds = activityIds;
    }

    TeamEnum(String teamCode, String teamName, List<Long> activityIds) {
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.activityIds = activityIds;
    }

    public static TeamEnum getTeam(String teamCode){
        for (TeamEnum value : TeamEnum.values()) {
            if(value.getTeamCode().equals(teamCode)){
                return value;
            }
        }
        return null;
    }
}
