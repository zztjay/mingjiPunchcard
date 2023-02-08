package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.constants.TeamEnum;
import com.tencent.wxcloudrun.model.User;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author：zhoutao
 * @Date：2023/1/30 14:58
 */
class UserServiceTest {

    @Resource
    UserService userService;

    @Test
    void save() {
        User user = new User();
        user.setMemberNick("韬");
        user.setMemberName("周韬");
        user.setAvator("www.baidu.com");
        user.setMemberOpenId("3");
        TeamEnum teamEnum = TeamEnum.getTeam("yuanli");
        user.setTeamCode(teamEnum.getTeamCode());
        System.out.println(userService.save(user));
    }
}