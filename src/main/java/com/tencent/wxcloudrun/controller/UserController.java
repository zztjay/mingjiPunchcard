package com.tencent.wxcloudrun.controller;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.CounterRequest;
import com.tencent.wxcloudrun.dto.UserDTO;
import com.tencent.wxcloudrun.dto.UserRequest;
import com.tencent.wxcloudrun.model.Counter;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.CounterService;
import com.tencent.wxcloudrun.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
     * 注册用户信息
     * @return API response json
     */
    @PostMapping(value = "/api/user/register")
    ApiResponse register(HttpServletRequest servletRequest, @RequestBody UserRequest request) {
        String token = JMockData.mock(String.class);
        log.warn("request is {} ", request);

//        User user = new User();
//        user.setMemberOpenId(LoginContext.getOpenId());
//        user.setMemberNick(request.getNick());
//        user.setAvator(request.getAvator());
//        user.setPhoneNumber(request.getPhoneNum());
//        user.setMemberName(request.getUserName());

//        userService.save(user);

        return ApiResponse.ok(token);
    }

    /**
     * 注册用户信息
     * @return API response json
     */
    @GetMapping(value = "/api/user/get")
    ApiResponse get(HttpServletRequest servletRequest) {
        UserDTO user  = JMockData.mock(UserDTO.class);
        log.warn("request is {} ", user);

//        User user = new User();
//        user.setMemberOpenId(LoginContext.getOpenId());
//        user.setMemberNick(request.getNick());
//        user.setAvator(request.getAvator());
//        user.setPhoneNumber(request.getPhoneNum());
//        user.setMemberName(request.getUserName());

//        userService.save(user);

        return ApiResponse.ok(user);
    }

    public static void main(String[] args) {
        UserController userController  = new UserController();
    }
}

