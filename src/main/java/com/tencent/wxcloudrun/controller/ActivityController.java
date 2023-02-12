package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.ActivityDTO;
import com.tencent.wxcloudrun.dto.ActivityQuery;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.dto.UserRequest;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.ActivityService;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 活动的页面控制器
 *
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class ActivityController {
    @Resource
    ActivityService activityService;

    /**
     * 生成活动信息
     *
     * @return API response json
     */
    @PostMapping(value = "/api/activity/save")
    ApiResponse save(@RequestBody Activity activity) {
        activityService.save(activity);
        return ApiResponse.ok();
    }

    /**
     * 获得活动列表
     *
     * @return API response json
     */
    @GetMapping(value = "/api/activity/query")
    ApiResponse query(HttpServletRequest request, ActivityQuery query) {
        Page<Activity> activitys = activityService.query(query);
        return ApiResponse.ok(activitys);
    }

    /**
     * 获得活动信息
     *
     * @return API response json
     */
    @GetMapping(value = "/api/activity/get")
    ApiResponse get(@RequestParam long activityId) {
        Activity activity = activityService.getById(activityId);
        return ApiResponse.ok(activity);
    }

    /**
     * 报名活动
     *
     * @return API response json
     */
    @PostMapping(value = "/api/activity/join")
    ApiResponse join(@RequestParam Long activityId
            , @RequestParam String userName) {
        return activityService.join(activityId, userName);

    }


}
