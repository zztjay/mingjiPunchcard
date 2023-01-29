package com.tencent.wxcloudrun.model;

import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队活动表
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "Activitys")
@Data
@NameStyle(Style.normal)
public class Activity implements Serializable {
    public static final int IS_SUPPORT_REPUNCHCRD = 1;
    public static final int IS_NOT_SUPPORT_REPUNCHCRD = 0;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private String teamCode; // 企业组织唯一标识码
    private String  activityName; // 活动名称
    private String  activityDesc;// 活动描述
    private String  activityPic;// 活动图片
    private LocalDateTime activityStartTime;// 开始生效时间
    private LocalDateTime activityEndTime;// 结束生效时间
    private Integer punchCardType;// 打卡类型，1：“整体文本法” 2.“标准造句法”
    private Integer punchCardFrequecy;// 打卡频次，1：“每天1次”，2. “每周6天”，3. “每周5天”
    private Integer canRepunchCard;// 是否允许补卡，0：不允许，1：允许
    private Integer repunchCardDays;// 允许补卡天数
    private String  members;// 全部学生名单, 格式：memberName,deptName,positionName,groupIdentifier
    private String  coachs;// 全部教练列表，包含openId和名称
        // [{
        // openId：教练的openId
        // name：教练名称
        // }]
    private String  rewardRule;// 奖励规则，json结构
        //[{
        //  type：积分类型，1.打卡 2.点赞、3.评级 4. 优选
        //  startTime：开始时间
        //  basePioint：基础值
        //  limit：积分上限
        //}]
    private String  ext;// 扩展字段
}
