package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.model.Activity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author：zhoutao
 * @Date：2023/1/28 17:52
 */
@SpringBootTest
class ActivityServiceTest {

    @Autowired
    ActivityService activityService;

    @Test
    void save() {
        Activity activity = JMockData.mock(Activity.class);
        activity.setId(1L);
        activity.setCreatedAt(LocalDateTime.now());
        activity.setUpdatedAt(LocalDateTime.now());

        JSONArray coaches = new JSONArray();
        JSONObject coache1 = new JSONObject();
        coache1.put("openId","1");
        coache1.put("name","国晖");
        coaches.add(coache1);

        JSONObject member = new JSONObject();
        member.put("memberName","周韬");
        member.put("deptName","部门");
        member.put("positionName","技术");
        member.put("groupIdentifier","分组1");
        activity.setMembers(new JSONArray().fluentAdd(member).toJSONString());

        Long id = activityService.save(activity);
        System.out.println(id);
    }

    @Test
    void query() {

    }

    @Test
    void getById() {
        System.out.println(activityService.getById(1));
    }



    @Test
    void main() {
    }
}