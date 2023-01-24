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
    private List<CommentDTO> comments;
}
