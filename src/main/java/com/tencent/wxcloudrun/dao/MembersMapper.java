package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.model.Record;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface MembersMapper extends Mapper<Member> {

    public Member selectByUserName(@Param("userName") String userName, @Param("activityId")  Long activityId);

    public Member selectByOpenId(@Param("openId")String openId,@Param("activityId") Long activityId);

}
