package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Record;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface PunchCardMapper extends Mapper<Record> {
    public int getRepunchCount(long activityId, String openId); // 获取用户补卡次数
}
