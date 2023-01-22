package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * 团队成员mapper
 * @Author：zhoutao
 * @Date：2023/1/17 15:49
 */
@Repository
public interface UsersMapper extends Mapper<User> {
}
