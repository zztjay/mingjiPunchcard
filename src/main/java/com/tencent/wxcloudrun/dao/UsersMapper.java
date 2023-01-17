package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * 团队成员mapper
 * @Author：zhoutao
 * @Date：2023/1/17 15:49
 */
@org.apache.ibatis.annotations.Mapper
public interface UsersMapper extends Mapper<User> {
}
