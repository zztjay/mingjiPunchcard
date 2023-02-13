package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.dto.ActivityQuery;
import com.tencent.wxcloudrun.dto.CommentQuery;
import com.tencent.wxcloudrun.model.Comment;
import com.tencent.wxcloudrun.model.Reward;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 评论mapper
 */
@Repository
public interface CommentMapper extends Mapper<Comment> {
    List<Comment> getRootComments(@Param("punchCardId")  Long punchCardId);
    List<Comment> getComments(@Param("rootCommentId") Long rootCommentId);
    List<Comment> queryRootComments(CommentQuery query);
    int countRootComments(CommentQuery query);
}
