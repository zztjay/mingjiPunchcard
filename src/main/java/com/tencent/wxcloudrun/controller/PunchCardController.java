package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommentDTO;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.dto.PunchCardRequest;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.ActivityService;
import com.tencent.wxcloudrun.service.PunchCardService;
import com.tencent.wxcloudrun.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 活动打卡页面控制器
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class PunchCardController {

    @Resource
    PunchCardService punchCardService;

    @Resource
    ActivityService activityService;

    /**
     * 活动打卡服务
     * @return API response json
     */
    @PostMapping(value = "/api/punchcard")
    ApiResponse punchcard(@RequestBody PunchCardRequest punchCardRequest) {
       return punchCardService.punchcard(punchCardRequest.getId(),punchCardRequest.getContent()
               ,punchCardRequest.getActivityId(),punchCardRequest.getPunchCardTime());
    }

    /**
     * 查询打卡列表
     * @return API response json
     */
    @GetMapping(value = "/api/punchcard/query")
    ApiResponse query(PunchCardQuery query) {
        Page<PunchCardDTO> records = punchCardService.query(query);
        return ApiResponse.ok(records);
    }

    /**
     * 查询打卡日历
     * @return API response json
     */
    @GetMapping(value = "/api/punchcard/calender")
    ApiResponse calender(Long activityId) {

        // 计算总共的活动时间
        Activity activity = activityService.getById(activityId);
        Date startDate = DateUtil.asDate(activity.getActivityStartTime());
        Date endDate = DateUtil.asDate(activity.getActivityEndTime());
        Long days = DateUtil.getBetweenDays(startDate,endDate);

        // 查询所有的打卡日历
        PunchCardQuery query = new PunchCardQuery();
        query.setActivityId(activityId);
        query.setPageSize(days.intValue());
        Page<PunchCardDTO> records = punchCardService.query(query);

        return ApiResponse.ok(records);
    }

    /**
     * 查询打卡记录
     * @return API response json
     */
    @GetMapping(value = "/api/punchcard/get")
    ApiResponse get(@RequestParam long recordId) {
        PunchCardDTO record = punchCardService.getPunchCardRecord(recordId);
        return ApiResponse.ok(record);
    }

}
