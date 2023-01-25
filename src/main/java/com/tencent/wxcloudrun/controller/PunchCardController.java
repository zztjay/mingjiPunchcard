package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommentDTO;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动打卡页面控制器
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class PunchCardController {


    /**
     * 活动打卡服务
     * @return API response json
     */
    @PostMapping(value = "/api/punchcard")
    ApiResponse punchcard(@RequestBody Record record) {
        return ApiResponse.ok();
    }

    /**
     * 查询活动打卡
     * @return API response json
     */
    @GetMapping(value = "/api/punchcard/query")
    ApiResponse query(@RequestParam PunchCardQuery query) {
        Page<PunchCardDTO> records = JMockData.mock(Page.class);
        return ApiResponse.ok(records);
    }



}
