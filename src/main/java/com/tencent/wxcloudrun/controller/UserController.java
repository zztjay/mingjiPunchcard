package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CounterService;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 用户表的页面控制器
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@RestController
@Slf4j
public class UserController {
    @Resource
    UserService userService;
    /**
     * 获取当前计数
     * @return API response json
     */
    @GetMapping(value = "/api/user")
    ApiResponse save() {
        log.info("/api/user get request");
        User user = new User();
        user.setMemberName("测试name");
        user.setMemberNick("测试nick");
        user.setPhoneNumber("15888853898");
        user.setTeamCode("teamcode");
        int len = userService.insertUser(user);
        log.info("/api/user get request, len:{}",len);
        return ApiResponse.ok();
    }
}
