package com.tencent.wxcloudrun.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队
 * @Author：zhoutao
 * @Date：2023/1/18 19:59
 */
@Data
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private String code; //  组织唯一标识码
    private String name; //  组织名称
    private String desc; // 组织描述
    private String ext; // 扩展信息
}
