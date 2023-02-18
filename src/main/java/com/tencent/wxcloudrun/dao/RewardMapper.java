package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.dto.RewardQuery;
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
    List<Reward> getByRecordId(@Param("recordId") Long recordId,
                                      @Param("type") int type);

    Reward getByGiveUserId(@Param("recordId") Long recordId, @Param("giveRewardUserId") String giveRewardUserId,
                               @Param("type") int type);

    List<Reward> query(RewardQuery query);

    int count(RewardQuery query);

    int sumRewardPoint(RewardQuery query);

    int sumRewardPointRank(RewardQuery query);
}
