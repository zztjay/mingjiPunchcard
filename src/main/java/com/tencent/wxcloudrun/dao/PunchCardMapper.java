package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.dto.ActivityQuery;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface PunchCardMapper extends Mapper<Record> {
    public int getRepunchCount(@Param("activityId")long activityId, @Param("openId") String openId); // 获取用户补卡次数

    List<Record> query(PunchCardQuery query);

    int count(PunchCardQuery query);
}
