package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.common.QueryBase;
import lombok.Data;

import java.util.List;

/**
 * 打卡评论查询
 * @Author：zhoutao
 * @Date：2023/1/25 16:47
 */
@Data
public class CommentQuery extends QueryBase {
    private String userId;
}
