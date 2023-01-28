package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.model.Record;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface MembersMapper extends Mapper<Member> {

    public Member selectByUserName(String userName, Long activityId);

    public Member selectByOpenId(String openId, Long activityId);

}
