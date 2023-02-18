package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.QueryBase;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 打卡查询
 * @Author：zhoutao
 * @Date：2023/1/25 16:47
 */
@Data
public class PunchCardQuery extends QueryBase {
    private Long activityId; //活动id
    private String punchCardTime; // 打卡日期
    private String openId; // 用户的openId
    private boolean userRecordToTop = false; // 当前用户记录置顶
    private List<Long> ingoreRecordIds = new ArrayList<>(); // 第一个记录

}
