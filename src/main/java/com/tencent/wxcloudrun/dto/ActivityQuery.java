package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.QueryBase;
import lombok.Data;

/**
 * 活动查询
 * @Author：zhoutao
 * @Date：2023/1/25 16:47
 */
@Data
public class ActivityQuery extends QueryBase {
    private String teamCode; // 用户的openId

    public ActivityQuery(String teamCode) {
        this.teamCode = teamCode;
    }
}
