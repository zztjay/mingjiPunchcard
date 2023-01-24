package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.ActivityDTO;
import com.tencent.wxcloudrun.dto.UserRequest;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动的页面控制器
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class ActivityController {

    /**
     * 生成活动信息
     * @return API response json
     */
    @PostMapping(value = "/api/activity/save")
    ApiResponse save(@RequestBody Activity activity) {
       return ApiResponse.ok();
    }

    /**
     * 获得活动列表
     * @return API response json
     */
    @GetMapping(value = "/api/activity/query")
    ApiResponse query() {
        List<ActivityDTO> activitys = JMockData.mock(ArrayList.class);
        return ApiResponse.ok(activitys);
    }
    /**
     * 获得活动信息
     * @return API response json
     */
    @GetMapping(value = "/api/activity/get")
    ApiResponse get(@RequestParam Long activityId) {
        Activity activity = JMockData.mock(Activity.class);
        return ApiResponse.ok(activity);
    }

    /**
     * 报名活动
     * @return API response json
     */
    @PostMapping(value = "/api/activity/join")
    ApiResponse join(@RequestParam String activityId
            , @RequestParam String userName) {
        return ApiResponse.ok();
    }


}
