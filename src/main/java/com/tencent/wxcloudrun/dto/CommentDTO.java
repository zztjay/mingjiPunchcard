package com.tencent.wxcloudrun.dto;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 评论回复记录
 *
 * @Author：zhoutao
 * @Date：2023/1/18 13:33
 */

@Data
public class CommentDTO {
    private Long id; // 唯一id
    private Long punchCardId; // 打卡记录id
    private Long rootCommentId; //  回复的根评论ID，第一次为空
    private String rootCommentContentType; //  评论内容类型，full. 完整句，positive. 正向，inpositive.负向，iwant：我还想做什么，thoughts：感想
    private String rootCommentContent; //  评论内容
    private String  receiveUserName; // 被评论用户的名称
    private String  commentUserName; // 评论用户的名称
    private String avatar; //  用户头像
    private String createAt; // 评论时间
    private int type; // 评论类型，1:评论，2:回复
    private String content; // 评论内容
}
