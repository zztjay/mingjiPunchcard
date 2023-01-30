package com.tencent.wxcloudrun.service;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.model.PunchCardContent;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author：zhoutao
 * @Date：2023/1/30 15:19
 */
class PunchCardServiceTest {
    @Resource
    PunchCardService punchCardService;

    @Test
    void punchcard() {
        PunchCardContent content = JMockData.mock(PunchCardContent.class);
    }

    @Test
    void query() {
    }

    @Test
    void getPunchCardRecord() {

    }
}