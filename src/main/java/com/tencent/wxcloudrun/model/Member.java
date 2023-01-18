package com.tencent.wxcloudrun.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队成员对象
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "Members")
@Data
@NameStyle(Style.normal)
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private Long activityId; // 活动id
    private String memberOpenId; // 用户的openId
    private String memberName; // 用户名称
    private String deptName; // 部门
    private String positionName; // 岗位
    private String groupIdentifier; // 所属分组
}
