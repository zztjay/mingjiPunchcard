package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommentDTO;
import com.tencent.wxcloudrun.dto.CommentQuery;
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
public class RewardController {


    /**
     * 活动打卡评论服务
     * @return API response json
     */
    @PostMapping(value = "/api/comment")
    ApiResponse comment(@RequestParam Long punchCardId, // 打卡记录id
                        @RequestParam Long rootCommentId, //  回复的根评论ID，第一次为空
                        @RequestParam int rootCommentContentType, //  评论内容类型，full. 完整句，positive. 正向，inpositive.负向，iwant：我还想做什么，thoughts：感想
                        @RequestParam Long replyCommentId, //  被回复的评论ID，评论为空
                        @RequestParam int type // 评论类型，1:评论，2:回复
                        ) {
        return ApiResponse.ok();
    }

    /**
     * 活动打卡评论查询服务，包含被评论的内容
     * @return API response json
     */
    @GetMapping(value = "/api/comment/query")
    ApiResponse query(@RequestParam CommentQuery query) {
        Page<CommentDTO> comments = JMockData.mock(Page.class);
        return ApiResponse.ok(comments);
    }

    /**
     * 活动打卡评分服务
     * @return API response json
     */
    @GetMapping(value = "/api/reward")
    ApiResponse reward(@RequestParam Long punchCardId,
                       @RequestParam Integer rewardType) {
        return ApiResponse.ok();
    }

}
