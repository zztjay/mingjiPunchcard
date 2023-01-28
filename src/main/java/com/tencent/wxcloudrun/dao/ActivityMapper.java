package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ActivityMapper extends Mapper<Activity> {

}
