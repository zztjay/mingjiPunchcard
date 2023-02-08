//package com.tencent.wxcloudrun.service;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.github.jsonzou.jmockdata.JMockData;
//import com.tencent.wxcloudrun.constants.TeamEnum;
//import com.tencent.wxcloudrun.dto.ActivityQuery;
//import com.tencent.wxcloudrun.dto.PunchCardContent;
//import com.tencent.wxcloudrun.dto.PunchCardQuery;
//import com.tencent.wxcloudrun.model.Activity;
//import com.tencent.wxcloudrun.model.Reward;
//import com.tencent.wxcloudrun.model.User;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//
///**
// * @Author：zhoutao
// * @Date：2023/1/31 17:03
// */
//@SpringBootTest
//public class FullProductTest {
//    @Resource
//    UserService userService;
//
//    @Autowired
//    ActivityService activityService;
//
//    @Resource
//    PunchCardService punchCardService;
//
//    @Resource
//    RewardService rewardService;
//
//
//    @Test
//    void fullTest() {
//
//        // 保存用户信息
//        User user = new User();
//        user.setMemberNick("韬");
//        user.setMemberName("周韬");
//        user.setAvator("www.baidu.com");
//        user.setMemberOpenId("3");
//        TeamEnum teamEnum = TeamEnum.getTeam("yuanli");
//        user.setTeamCode(teamEnum.getTeamCode());
//        System.out.println(userService.save(user));
//
////         创建活动信息
//        Activity activity = JMockData.mock(Activity.class);
//        activity.setCreatedAt(LocalDateTime.now());
//        activity.setUpdatedAt(LocalDateTime.now());
//
//        JSONArray coaches = new JSONArray();
//        JSONObject coache1 = new JSONObject();
//        coache1.put("openId","1");
//        coache1.put("name","国晖");
//        coaches.add(coache1);
//        activity.setMembers("周韬,部门,技术,分组");
//        activity.setId(null);
//
//        Long id = activityService.save(activity);
//
//
//        activityService.join(id, "周韬");
//
//        PunchCardContent content = JMockData.mock(PunchCardContent.class);
//        long recordId = (long) punchCardService.punchcard(JSON.toJSONString(content), 5L, LocalDateTime.now()).getData();
//
//       long commentId = (Long)rewardService.comment(recordId,"full",null,"写得真好").getData();
//        rewardService.comment(recordId,null,commentId,"写得真好的回复");
//        System.out.println(rewardService.getComments(recordId));
//
//        rewardService.reward(recordId,Reward.REWARD_TYPE_THUMBS_UP,1);
//
//        System.out.println(punchCardService.getPunchCardRecord(recordId));
//
//    }
//
//
//    @Test
//    public void queryTest(){
//        activityService.query(new ActivityQuery("yuanli"));
//        punchCardService.query(new PunchCardQuery());
//    }
//
//
//}
