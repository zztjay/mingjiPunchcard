package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.User;

/**
 * 团队成员业务器
 * @Author：zhoutao
 * @Date：2023/1/17 16:38
 */
public interface UserService {
    int insertUser(User user);
}
