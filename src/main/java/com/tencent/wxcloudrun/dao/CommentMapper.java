package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Reward;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 评论mapper
 */
@Repository
public interface CommentMapper extends Mapper<Comment> {
    List<Comment> getRootComments(Long punchCardId);
    List<Comment> getComments(Long rootCommentId);
}
