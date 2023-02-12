package com.tencent.wxcloudrun.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 评论请求
 *
 * @Author：zhoutao
 * @Date：2023/2/12 22:55
 */
@Data
public class CommentRequest {
   private  long punchCardId; // 打卡记录id
   private  String rootCommentContentType; //  评论内容类型，full. 完整句，positive. 正向，inpositive.负向，iwant：我还想做什么，thoughts：感想
   private long replyCommentId; //  被回复的评论ID，评论为空
   private String content;
}
