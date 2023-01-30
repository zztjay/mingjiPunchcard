package com.tencent.wxcloudrun.model;

import lombok.Data;

/**
 * @Author：zhoutao
 * @Date：2023/1/30 15:22
 */
@Data
public class PunchCardContent {
    private int type; //内部还是外部客户，1:内部，2:外部
    private InnerContent positive; // 正向
    private InnerContent inpositive; // 正向
    private String iwant; // 我还想做什么
    private String thoughts; // 感想
    class InnerContent{
        public String full; // 表示完整的
        public String toWho; // 对象
        public String scene; // 场景
        public String action; // 行为
        public String thoughts; // 感知
        public String withWho; // 联动
    }

}
