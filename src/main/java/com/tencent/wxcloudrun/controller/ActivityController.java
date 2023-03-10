package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.util.StringUtils;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CoachEnum;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    public ApiResponse save(@RequestBody Activity activity) {
       return activityService.save(activity);

    }

    /**
     * 获得管理员活动列表
     *
     * @return API response json
     */
    @GetMapping(value = "/api/activity/query")
    public  ApiResponse query(HttpServletRequest request, ActivityQuery query) {
        Page<Activity> activitys = activityService.query(query);
        return ApiResponse.ok(activitys);
    }

    /**
     * 获得管理员活动列表
     *
     * @return API response json
     */
    @GetMapping(value = "/api/activity/signlist")
    public ApiResponse signList(HttpServletRequest request, ActivityQuery query) {
        List<Activity> activitys = activityService.signList(LoginContext.getOpenId());
        return ApiResponse.ok(activitys);
    }




    /**
     * 获得活动信息
     *
     * @return API response json
     */
    @GetMapping(value = "/api/activity/get")
    public ApiResponse get(@RequestParam long activityId) {
        Activity activity = activityService.getById(activityId);
        return ApiResponse.ok(activity);
    }

    /**
     * 报名活动
     *
     * @return API response json
     */
    @GetMapping(value = "/api/activity/join")
    public ApiResponse join(@RequestParam long activityId
            , @RequestParam String userName) {
        return activityService.join(activityId, userName);

    }
}
