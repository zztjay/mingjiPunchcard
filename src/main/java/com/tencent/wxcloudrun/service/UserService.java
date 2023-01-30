package com.tencent.wxcloudrun.service;

import com.github.jsonzou.jmockdata.util.StringUtils;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.dao.UsersMapper;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 团队成员业务器
 * @Author：zhoutao
 * @Date：2023/1/17 16:38
 */
@Service
public class UserService {
    @Resource
    UsersMapper usersMapper;

    public int save(User user) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(user.getMemberOpenId())
            && StringUtils.isNotEmpty(user.getMemberNick()) );
        if(user.getId() != null && user.getId() > 0L){
             usersMapper.updateByPrimaryKey(user);
             return user.getId().intValue();
        } else {
            return usersMapper.insert(user);
        }
    }
}
