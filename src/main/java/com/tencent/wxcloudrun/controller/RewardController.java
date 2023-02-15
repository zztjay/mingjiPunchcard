package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommentDTO;
import com.tencent.wxcloudrun.dto.CommentQuery;
import com.tencent.wxcloudrun.dto.CommentRequest;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 活动打卡页面控制器
 *
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class RewardController {
    @Resource
    RewardService rewardService;


    /**
     * 活动打卡评论服务
     *
     * @return API response json
     */
    @PostMapping(value = "/api/comment")
    ApiResponse comment(@RequestBody CommentRequest commentRequest) {
        return rewardService.comment(commentRequest.getPunchCardId()
                , commentRequest.getRootCommentContentType()
                , commentRequest.getReplyCommentId()
                , commentRequest.getContent());
    }

    /**
     * 活动打卡评论查询服务，包含被评论的内容
     *
     * @return API response json
     */
    @GetMapping(value = "/api/comment/query")
    ApiResponse query(int pageSize, int currentPage) {
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setUserId(LoginContext.getOpenId());
        commentQuery.setPageSize(pageSize);
        commentQuery.setCurrentPage(currentPage);
        Page<List<CommentDTO>> comments = rewardService.getUserLastComments(commentQuery);
        return ApiResponse.ok(comments);
    }

    /**
     * 活动打卡评分服务
     *
     * @return API response json
     */
    @GetMapping(value = "/api/reward")
    ApiResponse reward(@RequestParam long punchCardId,
                       @RequestParam int rewardType, @RequestParam int rewardPoint) {
        return rewardService.reward(punchCardId, rewardType, rewardPoint);
    }

}
