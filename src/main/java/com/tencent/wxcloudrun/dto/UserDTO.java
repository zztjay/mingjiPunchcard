package com.tencent.wxcloudrun.dto;

import lombok.Data;

/**
 * @Author：zhoutao
 * @Date：2023/2/12 15:52
 */
@Data
public class UserDTO {
    public static final int MANAGER_TYPE_SUPER = 1;
    public static final int MANAGER_TYPE_NO = 0;
    public static final int USER_TYPE_COACH = 2;
    public static final int USER_TYPE_NORMAL = 1;
    private int userType = 1; // 1：学员 2：教练
    private int managerType = 0 ; // 0: 无身份 1：超级管理员
    private String avator; // 头像链接
    private String memberName; //用户名称，一开始默认用微信昵称
    private String phoneNumber; //手机号
}
