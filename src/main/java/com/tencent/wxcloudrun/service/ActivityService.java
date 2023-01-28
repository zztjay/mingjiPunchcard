package com.tencent.wxcloudrun.service;

import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.util.StringUtils;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.dao.MembersMapper;
import com.tencent.wxcloudrun.dao.PunchCardMapper;
import com.tencent.wxcloudrun.dto.ActivityQuery;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 活动业务逻辑处理器
 *
 * @Author：zhoutao
 * @Date：2023/1/26 11:11
 */
@Service
public class ActivityService {
    @Resource
    ActivityMapper activityMapper;

    @Resource
    MembersMapper membersMapper;

    public int save(Activity activity) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(
                activity.getActivityName()) && null != activity.getActivityStartTime()
                && null != activity.getActivityEndTime() && StringUtils.isNotEmpty(activity.getCoachs())
                && StringUtils.isNotEmpty(activity.getMembers()));
        if (activity.getId() != null && activity.getId() > 0L) {
            activityMapper.updateByPrimaryKey(activity);
            return activity.getId().intValue();
        } else {
            return activityMapper.insert(activity);
        }
    }


    /**
     * 获得报名活动列表
     *
     * @return API response json
     */
    public Page<Activity> query(ActivityQuery query) {
        Page<Activity> page = new Page<>();
        int count = activityMapper.count(query);
        if(count > 0){
            page.setEntityList(activityMapper.query(query));
        }
        page.setTotalRecords(count);
        return page;
    }

    /**
     * 获得活动信息
     *
     * @return API response json
     */
    public Activity getById(long activityId) {
        Preconditions.checkArgument(activityId >= 0);
        return activityMapper.selectByPrimaryKey(activityId);
    }

    /**
     * 报名活动
     *
     * @return API response json
     */
    // todo
    public ApiResponse join(Long activityId, String userName) {
        Activity activity = getById(activityId);

        // 检查用户报名合法性
        if (!activity.getMembers().contains(userName)) {
            return ApiResponse.error("USER_NOT_EXSIT", "用户不存在");
        }
        if (membersMapper.selectByUserName(userName, activityId) != null) {
            return ApiResponse.error("USER_ALREADY_SIGN", "用户已报名");
        }

        // 提取成员信息，保存DB
        String memberInfos[] = activity.getMembers().split("\\r?\\n");
        for (String memberInfo : memberInfos) {
            if (memberInfo.contains(userName)) {
                String[] result = memberInfo.split(",");
                if (result.length != 4) {
                    return ApiResponse.error("MEMBER_DATA_FORMAT_ERROR", "活动名单格式有误");
                }
                String memberName = result[0]; // 用户名称
                String deptName = result[1]; // 部门
                String positionName = result[2]; // 岗位
                String groupIdentifier = result[3]; // 所属分组

                // 保存活动名单信息
                Member member = new Member();
                member.setActivityId(activityId);
                member.setMemberName(memberName);
                member.setMemberOpenId(LoginContext.getOpenId());
                member.setDeptName(deptName);
                member.setPositionName(positionName);
                member.setGroupIdentifier(groupIdentifier);
                membersMapper.insert(member);

                break;
            }
        }
        return ApiResponse.ok();
    }

    public static void main(String[] args) {
        String a = "abc\n" +
                "def\n" +
                "ghi";
        String members[] = a.split("\\r?\\n");
    }
}