package com.tencent.wxcloudrun.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

/**
 * @Author：zhoutao
 * @Date：2023/2/12 22:45
 */
@Data
public class PunchCardRequest {
    private Long id;
    private String content;
    private long activityId;
    private String punchCardTime;
}
