package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CommentDTO;
import com.tencent.wxcloudrun.dto.CommentQuery;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    ApiResponse comment(@RequestParam long punchCardId, // 打卡记录id
                        @RequestParam int rootCommentContentType, //  评论内容类型，full. 完整句，positive. 正向，inpositive.负向，iwant：我还想做什么，thoughts：感想
                        @RequestParam long replyCommentId //  被回复的评论ID，评论为空
                        ) {
        return ApiResponse.ok();
    }

    /**
     * 活动打卡评论查询服务，包含被评论的内容
     * @return API response json
     */
    @GetMapping(value = "/api/comment/query")
    ApiResponse query(CommentQuery query) {
        CommentDTO commentDTO = JMockData.mock(CommentDTO.class);
        Page<CommentDTO> comments = new Page<>();
        comments.setEntityList(Arrays.asList(commentDTO));
        return ApiResponse.ok(comments);
    }

    /**
     * 活动打卡评分服务
     * @return API response json
     */
    @PostMapping(value = "/api/reward")
    ApiResponse reward(@RequestParam long punchCardId,
                       @RequestParam int rewardType,@RequestParam int rewardPoint) {
        return ApiResponse.ok();
    }

}
