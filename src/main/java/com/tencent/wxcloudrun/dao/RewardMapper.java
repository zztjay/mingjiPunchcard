package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.model.Reward;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 奖励mapper
 */
@Repository
public interface RewardMapper extends Mapper<Reward> {
    public List<Reward> getByRecordId(@Param("recordId") Long recordId, @Param("openId") String openId,
                                      @Param("type") int type);
}
