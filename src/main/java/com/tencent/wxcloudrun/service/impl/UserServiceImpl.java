package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UsersMapper;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 团队成员逻辑处理器
 * @Author：zhoutao
 * @Date：2023/1/17 16:40
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UsersMapper usersMapper;
    @Override
    public int insertUser(User user) {
      return usersMapper.insert(user);
    }
}
