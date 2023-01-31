package com.tencent.wxcloudrun.dto;

/**
 * 内部内容
 * @Author：zhoutao
 * @Date：2023/1/31 16:59
 */
public class InnerContent {
    public String full; // 表示完整的
    public String toWho; // 对象
    public String scene; // 场景
    public String action; // 行为
    public String thoughts; // 感知
    public String withWho; // 联动

    public InnerContent() {
    }

    public InnerContent(String full, String toWho, String scene, String action, String thoughts, String withWho) {
        this.full = full;
        this.toWho = toWho;
        this.scene = scene;
        this.action = action;
        this.thoughts = thoughts;
        this.withWho = withWho;
    }
}
