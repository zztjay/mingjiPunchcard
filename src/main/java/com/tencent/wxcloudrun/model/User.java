package com.tencent.wxcloudrun.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.util.StringUtils;
import lombok.Data;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 团队成员对象
 *
 * @Author：zhoutao
 * @Date：2023/1/17 15:40
 */
@Table(name = "Users")
@Data
@NameStyle(Style.normal)
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 唯一id
    private LocalDateTime createdAt;// 创建时间
    private LocalDateTime updatedAt;// 修改时间
    private String teamCode; // 组织唯一标识码
    private String avator; // 头像链接
    private String memberOpenId; // 用户的openId
    private String memberName; //用户名称，一开始默认用微信昵称
    private String memberNick; // 用户微信昵称
    private String phoneNumber; //手机号
    private String ext; // 扩展信息

    public void addExt(String key, Object value) {
        JSONObject newExt = getExtJSONValue();
        newExt.put(key,value);
        ext = newExt.toJSONString();
    }

    public JSONObject getExtJSONValue() {
        if (StringUtils.isEmpty(ext)) {
            return new JSONObject();
        }
        return JSONObject.parseObject(ext);
    }
}
