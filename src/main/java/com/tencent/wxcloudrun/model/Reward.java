package com.tencent.wxcloudrun.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队奖励
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "Rewards")
@Data
@NameStyle(Style.normal)
public class Reward implements Serializable {
    private static final long serialVersionUID = 1L;


    public static final int REWARD_TYPE_PUNCH_CARD = 1;
    public static final int REWARD_TYPE_THUMBS_UP = 2;
    public static final int REWARD_TYPE_LEVE = 3;
    public static final int REWARD_TYPE_BEST = 4;

    public static final int REWARD_USRE_TYPE_MEMBER = 1;
    public static final int REWARD_USRE_TYPE_COACH = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private Long activityId; // 活动id
    private Long punchCardId; // 打卡记录id
    private Integer rewardType; // 奖励类型，1.打卡 2.点赞、3.评级 4. 优选
    private String userOpenId; // 用户的openId
    private String giveRewardUserId; // 送出奖励用户的openId
    private Integer giveRewardUserType; // 奖励用户类型，1:成员，2: 教练
    private Integer rewardPoint; // 奖励积分，通过积分规则计算
    private Integer rewardLevel; // 奖励等级
}
