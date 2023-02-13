package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.QueryBase;
import lombok.Data;

/**
 * 奖励查询
 * @Author：zhoutao
 * @Date：2023/1/25 16:47
 */
@Data
public class PunchCardQuery extends QueryBase {
    private Long activityId; //活动id
    private String punchCardTime; // 打卡日期
    private String openId; // 用户的openId

}
