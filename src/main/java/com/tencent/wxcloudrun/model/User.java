package com.tencent.wxcloudrun.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队成员对象
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "Users")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    @Column(name = "createdAt")
    private LocalDateTime createdAt;// 创建时间
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;// 修改时间
    @Column(name = "teamCode")
    private String teamCode; // 组织唯一标识码
    @Column(name = "memberOpenId")
    private String memberOpenId; // 用户的openId
    @Column(name = "memberName")
    private String memberName; //用户名称，一开始默认用微信昵称
    @Column(name = "memberNick")
    private String memberNick; // 用户微信昵称
    @Column(name = "phoneNumber")
    private String phoneNumber; //手机号
}
