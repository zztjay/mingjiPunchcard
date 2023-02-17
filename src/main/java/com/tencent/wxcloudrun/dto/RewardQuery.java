package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.QueryBase;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * 打卡评论查询
 *
 * @Author：zhoutao
 * @Date：2023/1/25 16:47
 */
@Data
public class RewardQuery extends QueryBase {
    private Long activityId; // 打卡id
    private Long recordId; // 打卡id
    private String openId; // 用户id
    private Integer type; // 奖励类型
    private String giveRewardUserId; // 给予用户id

    private Integer sumRewardPoints; // 统计总分

    public RewardQuery(Long activityId, Long recordId, String openId, Integer type, String giveRewardUserId) {
        this.activityId = activityId;
        this.recordId = recordId;
        this.openId = openId;
        this.type = type;
        this.giveRewardUserId = giveRewardUserId;
    }

    public RewardQuery(Long activityId, String openId) {
        this.activityId = activityId;
        this.openId = openId;
    }

    public RewardQuery(Long activityId, String openId, Integer sumRewardPoints) {
        this.activityId = activityId;
        this.openId = openId;
        this.sumRewardPoints = sumRewardPoints;
    }
    public RewardQuery() {
    }
}
