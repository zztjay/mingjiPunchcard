//package com.tencent.wxcloudrun.service;
//
//import com.alibaba.fastjson.JSON;
//import com.github.jsonzou.jmockdata.JMockData;
//import com.tencent.wxcloudrun.dto.PunchCardContent;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//
//import java.time.LocalDateTime;
//
///**
// * 打卡服务测试
// * @Author：zhoutao
// * @Date：2023/1/30 15:19
// */
//@SpringBootTest
//class PunchCardServiceTest {
//
//    @Resource
//    PunchCardService punchCardService;
//
//    @Test
//    void punchcard() {
//        PunchCardContent content = JMockData.mock(PunchCardContent.class);
//        punchCardService.punchcard(JSON.toJSONString(content),1L, LocalDateTime.now());
//
//    }
//
//    @Test
//    void query() {
//    }
//
//    @Test
//    void getPunchCardRecord() {
//       // punchCardService.getPunchCardRecord(1L);
//    }
//}