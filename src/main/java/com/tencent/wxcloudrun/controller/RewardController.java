package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommentDTO;
import com.tencent.wxcloudrun.dto.CommentQuery;
import com.tencent.wxcloudrun.dto.CommentRequest;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.RewardService;
import com.tencent.wxcloudrun.service.UserService;
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

    @Resource
    UserService userService;

    /**
     * 活动打卡评论服务
     *
     * @return API response json
     */
    @PostMapping(value = "/api/comment")
    public   ApiResponse comment(@RequestBody CommentRequest commentRequest) {
        return rewardService.comment(commentRequest.getPunchCardId()
                , commentRequest.getRootCommentContentType()
                , commentRequest.getReplyCommentId()
                , commentRequest.getContent());
    }

    /**
     * 活动打卡评论查询服务，包含被评论的内容，对应小程序的"消息"
     *
     * @return API response json
     */
    @GetMapping(value = "/api/comment/query")
    public ApiResponse query(int pageSize, int currentPage) {
        CommentQuery commentQuery = new CommentQuery();
        commentQuery.setUserId(LoginContext.getOpenId());
        commentQuery.setPageSize(pageSize);
        commentQuery.setCurrentPage(currentPage);
        Page<List<CommentDTO>> comments = rewardService.getUserLastComments(commentQuery);
        return ApiResponse.ok(comments);
    }

    /**
     * 查看活动打卡存在多少未读评论
     *
     * @return API response json
     */
    @GetMapping(value = "/api/comment/unread")
    public  ApiResponse unread() {
       User user = userService.getUser(LoginContext.getOpenId());
       if(user.getExtJSONValue().getBooleanValue("unread") == false){
           return ApiResponse.ok(0);
       }
       return ApiResponse.ok(Integer.MAX_VALUE);
    }

    /**
     * 活动打卡评分服务
     *
     * @return API response json
     */
    @GetMapping(value = "/api/reward")
    public   ApiResponse reward(@RequestParam long punchCardId,
                       @RequestParam int rewardType, Integer rewardLevel) {
        return rewardService.reward(punchCardId, rewardType, rewardLevel);
    }

    /**
     * 活动打卡取消评分服务
     *
     */
    @GetMapping(value = "/api/reward/cancel")
    public   ApiResponse cancel(@RequestParam long punchCardId,
                       @RequestParam int rewardType) {
        return rewardService.cancel(punchCardId, rewardType);
    }

}
