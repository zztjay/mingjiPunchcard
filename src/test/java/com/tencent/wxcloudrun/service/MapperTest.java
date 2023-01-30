package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.ActivityQuery;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Author：zhoutao
 * @Date：2023/1/30 15:36
 */
@SpringBootTest
public class MapperTest {
    @Resource
    ActivityMapper activityMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    MembersMapper membersMapper;

    @Resource
    PunchCardMapper punchCardMapper;

    @Resource
    RewardMapper rewardMapper;

    @Resource
    UsersMapper usersMapper;

    @Test
    public void correctionTest(){
        usersMapper.getByOpenId("1");

//        activityMapper.query(new ActivityQuery("1"));
//        activityMapper.count(new ActivityQuery("1"));

        membersMapper.selectByOpenId("1",1L);
        membersMapper.selectByUserName("周韬",1L);

        commentMapper.getComments(1L);
        commentMapper.getRootComments(1L);

//        punchCardMapper.query(new PunchCardQuery());
//        punchCardMapper.count(new PunchCardQuery());
        punchCardMapper.getRepunchCount(1L, "1");

        rewardMapper.getByRecordId(1L,"1",1);

    }
}
