package com.tencent.wxcloudrun.model;

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
@Table(name = "Comments")
@Data
@NameStyle(Style.normal)
public class Comment {
    public static final int COMMENT_TYPE_COMMENT = 1;
    public static final int COMMENT_TYPE_REPLY = 2;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间

    private String avatar; // 头像
    private Long punchCardId; // 打卡记录id
    private Long rootCommentId; //  回复的根评论ID，第一次为空
    private String rootCommentContentType; //  评论内容类型，full. 完整句，positive. 正向，inpositive.负向，iwant：我还想做什么，thoughts：感想
    private String rootCommentContent; // 根评论的内容
    private Long replyCommentId; //  被回复的评论ID，第一次为空
    private Integer type; // 评论类型，1:评论，2:回复
    private String commentUserId; //  评论用户的openId
    private String commentUserName; //  评论用户的名称
    private Integer commentUserType; //  评论用户类型，1:成员，2: 教练
    private String  receiveUserId; // 被评论用户的openId
    private String  receiveUserName; // 被评论用户的名称
    private Integer receiveUserType; // 被评论用户类型，1:成员，2: 教练
    private String content; // 评论内容
}
