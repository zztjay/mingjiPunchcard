package com.tencent.wxcloudrun.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队成员对象
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "punch_card_team_members")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime gmtCreate; // 创建时间
    private LocalDateTime gmtModified;// 修改时间
    private String teamCode; // 组织唯一标识码
    private String memberOpenId; // 用户的openId
    private String memberName; //用户名称，一开始默认用微信昵称
    private String memberNick; // 用户微信昵称
    private String phoneNumber; //手机号
}
