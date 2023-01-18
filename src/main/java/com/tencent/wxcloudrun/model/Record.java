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
 * 打卡记录
 *
 * @Author：zhoutao
 * @Date：2023/1/18 13:33
 */
@Table(name = "Records")
@Data
@NameStyle(Style.normal)
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private String teamCode; // 企业组织唯一标识码
    private Long activityId; // 活动id
    private String memberOpenId; // 用户的openId
    private String groupIdentifier; // 所属分组
    private int isRepunchCard; // 是否为补卡
    private String content; //打卡内容，使用json结构存储
    private int type; //内部还是外部客户，1:内部，2:外部
//● positive：正向
//  ○ full：表示完整的
//  ○ toWho：对象
//  ○ scene：场景
//  ○ action：行为
//  ○ thoughts：感知
//  ○ withWho：联动
// ● inpositive：负向
//  ○ full：表示完整的
//  ○ toWho：对象
//  ○ scene：场景
//  ○ action：行为
//  ○ thoughts：感知
//  ○ withWho：联动
//● iwant：我还想做什么
//● thoughts：感想
}
