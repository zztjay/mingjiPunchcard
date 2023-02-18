package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 活动打卡DTO对象
 * @Author：zhoutao
 * @Date：2023/1/24 14:13
 */
@Data
public class PunchCardDTO implements Serializable {
    private Long activityId;
    private String content; // 打卡内容，使用json结构存储
    private Long recordId; // 打卡id
    private int thumbsUp; // 点赞数
    private boolean userThumbsup; // 用户点过赞
    private int level;// 评级分数, 0:暂不标记 -1：不符合要求， >0：评级分数
    private boolean isBest = false; // 优选
    private String punchCardTime; //  打卡时间
    private String userName; // 用户名称

    private Integer punchCardType; // 打卡类型，1：“整体文本法” 2.“标准造句法”
    private String avtar; // 头像
    private String createAt; // 打卡时间
    private String positionName; // 职位
    private String deptName; // 部门信息
    private String groupIdentifier; // 分组信息

    private List<List<CommentDTO>> comments; // 评论列表
}
