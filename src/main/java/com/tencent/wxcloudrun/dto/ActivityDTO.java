package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 活动的DTO对象
 * @Author：zhoutao
 * @Date：2023/1/24 14:13
 */
@Data
public class ActivityDTO implements Serializable {
    private String  activityName; // 活动名称
    private String  activityDesc;// 活动描述
    private String  activityPic;// 活动图片
    private String activityStartTime;// 开始生效时间
    private String activityEndTime;// 结束生效时间
    private int punchCardType;// 打卡类型，1：“整体文本法” 2.“标准造句法”
    private int punchCardFrequecy;// 打卡频次，1：“每天1次”，2. “每周6天”，3. “每周5天”
    private int canRepunchCard;// 是否允许补卡，0：不允许，1：允许
    private int repunchCardDays;// 允许补卡天数
    private String  members;// 全部学生名单
    private String  coachs;// 全部教练列表，包含openId和名称
    private String  rewardRule;// 奖励规则，json结构
    // {
        // type：积分类型，1.打卡 2.点赞、3.评级 4. 优选
        // startTime：开始时间
        // basePioint：基础值
        // limit：积分上限
    // }
    private int statistics; // 活动统计数据
    // {
        //  days: 打卡天数
        //  rate: 打卡率
        //  thumbups: 点赞数
        //  avgThumbups: 平均点赞个数/天
        //  totalScore：累计分数
        //  rank：排名
        //  total: 总人数
    // }
}
