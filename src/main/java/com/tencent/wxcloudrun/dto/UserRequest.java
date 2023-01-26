package com.tencent.wxcloudrun.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String nick; // 昵称
    private String avator; // 头像
    private String userName; // 名称
    private String phoneNum; // 电话号码
}
