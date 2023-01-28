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
    private String content; // 打卡内容，使用json结构存储
    private Long recordId; // 打卡id
    private int thumbsUp; // 点赞数
    private int level;// 评级分数, 0:暂不标记 -1：不符合要求， >0：评级分数
    private boolean isBest = false; // 优选
    private List<List<CommentDTO>> comments; // 评论列表
}
