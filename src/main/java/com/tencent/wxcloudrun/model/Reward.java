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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private Long punchCardId; // 打卡记录id
    private int rewardType; // 奖励类型，1.打卡 2.点赞、3.评级 4. 优选
    private String giveRewardUserId; // 送出奖励用户的openId
    private int giveRewardUserType; // 奖励用户类型，1:成员，2: 教练
    private int rewardPoint; // 奖励分数
}
