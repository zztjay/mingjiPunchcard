package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.util.StringUtils;
import com.google.common.base.Preconditions;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CoachEnum;
import com.tencent.wxcloudrun.constants.SuperManagerEnum;
import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.ActivityQuery;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.dto.RewardQuery;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Member;
import com.tencent.wxcloudrun.model.Reward;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

/**
 * 活动业务逻辑处理器
 *
 * @Author：zhoutao
 * @Date：2023/1/26 11:11
 */
@Service
@Slf4j
public class ActivityService {
    @Resource
    ActivityMapper activityMapper;

    @Resource
    MembersMapper membersMapper;

    @Resource
    PunchCardMapper punchCardMapper;

    @Resource
    UsersMapper usersMapper;

    @Resource
    UserService userService;

    @Resource
    RewardMapper rewardMapper;

    public ApiResponse save(Activity activity) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(
                activity.getActivityName()) && null != activity.getActivityStartTime()
                && null != activity.getActivityEndTime()
                && StringUtils.isNotEmpty(activity.getMembers()));

        // 检查是否为超级管理员
        if(!SuperManagerEnum.isSuper(LoginContext.getOpenId())){
            return ApiResponse.error("USER_HAS_NO_PERMISSION","用户没有权限创建活动");
        }

        // 解析教练逻辑
        JSONArray coaches = new JSONArray();
        for (CoachEnum coachEnum : CoachEnum.values()) {
            JSONObject coach = new JSONObject();
            coach.put("openId", coachEnum.getOpenId());
            coach.put("name", coachEnum.getName());
            coaches.add(coach);
        }
        activity.setCoachs(coaches.toJSONString());

        if (activity.getId() != null && activity.getId() > 0L) {
            activityMapper.updateByPrimaryKey(activity);
        } else {
            activityMapper.insert(activity);
        }
        return ApiResponse.ok(activity.getId());
    }

    public List<Activity> signList(String openId) {
        List<Activity> activityList = new ArrayList<>();
        List<Long> activityIds = membersMapper.getSignList(openId);
        for (Long activityId : activityIds) {
            Activity activity = getById(activityId);
            activityList.add(activity);
        }
        return activityList;
    }

    private void addStatistic(Activity activity){
        JSONObject statistic = new JSONObject();

        // 活动打卡天数
        Date startDate = DateUtil.asDate(activity.getActivityStartTime());
        Date endDate = new Date();
        Long punchCardDays = DateUtil.getBetweenDays(startDate,endDate);
        statistic.put("punchCardDays",punchCardDays); // 活动进行总天数

        // 打卡数，打卡率
        PunchCardQuery query = new PunchCardQuery();
        query.setActivityId(activity.getId());
        query.setOpenId(LoginContext.getOpenId());
        int punchCardCount = punchCardMapper.count(query);
        statistic.put("punchCardCount",punchCardCount);// 用户打卡天数

        if(null != punchCardDays &&  punchCardDays > 0){
            statistic.put("punchCardRate",new BigDecimal((double)punchCardCount/punchCardDays).setScale
                    (2,BigDecimal.ROUND_HALF_DOWN).toString()); // 打卡率
        }


        // 获赞总数，日均点赞数
        Integer thumbsupTotal = rewardMapper.count(new RewardQuery(activity.getId(),
                null,LoginContext.getOpenId(), Reward.REWARD_TYPE_THUMBS_UP, null));
        statistic.put("thumbsupTotal",thumbsupTotal); // 获赞总数

        if(null != punchCardDays &&  punchCardDays > 0){
            statistic.put("thumbsupAvg",new BigDecimal((double)thumbsupTotal/punchCardDays).setScale
                    (2,BigDecimal.ROUND_HALF_DOWN).toString() ); // 每天获赞数
        }

        // 总积分，排名
        int sumRewardPoint = rewardMapper.sumRewardPoint(new RewardQuery(activity.getId(),LoginContext.getOpenId()));
        statistic.put("sumRewardPoint",sumRewardPoint); // 获得总积分数
        int sumRewardPointRank = rewardMapper.sumRewardPointRank(
                new RewardQuery(activity.getId(),LoginContext.getOpenId(),sumRewardPoint));
        statistic.put("sumRewardPointRank",sumRewardPointRank); // 获得总积分数
        statistic.put("sumMembers",membersMapper.sumMembers(activity.getId())); // 总的参赛用户

        activity.setStatistic(statistic);
    }
    /**
     * 获得报名活动列表
     *
     * @return API response json
     */
    public Page<Activity> query(ActivityQuery query) {
        Page<Activity> page = new Page<>();
        // 超级管理员校验逻辑
        if(null == SuperManagerEnum.getByOpenId(LoginContext.getOpenId())){
            log.warn("user is not supermanager，openId:{}",LoginContext.getOpenId());
            return page;
        }
        User user = usersMapper.getByOpenId(LoginContext.getOpenId());
        query.setTeamCode(user.getTeamCode());
        int count = activityMapper.count(query);
        if (count > 0) {
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
        Activity activity = activityMapper.selectByPrimaryKey(activityId);

        // 提交分组信息
        if (StringUtils.isNotEmpty(activity.getMembers())) {
            List<JSONObject> groups = new ArrayList<>();
            Map<String, Map<String,Boolean>> map = getGroupMembers(activity.getMembers(),activityId);
            for (String groupName : map.keySet()) {
                JSONObject group = new JSONObject();
                group.put("groupName", groupName);
                groups.add(group);

                ArrayList<JSONObject> memberList= new ArrayList<>();
                group.put("memebers", memberList);
                Map<String,Boolean> memberMap = map.get(groupName);
                for (String memberName : memberMap.keySet()) {
                    JSONObject member = new JSONObject();
                    member.put("name", memberName);
                    member.put("isBind", memberMap.get(memberName));
                    memberList.add(member);
                }
            }
            activity.setGroupMembers(groups);
        }
        // 提取活动信息
        addStatistic(activity);

        // 剩余补卡天数
        if(activity.getCanRepunchCard() == Activity.IS_SUPPORT_REPUNCHCRD){
           int repunchCount = punchCardMapper.getRepunchCount(activityId, LoginContext.getOpenId());
           activity.setLeftRepunchCardDays(activity.getRepunchCardDays() - repunchCount);
        }


        return activity;
    }

    private Map<String, Map<String,Boolean>> getGroupMembers(String members, Long activityId) {

        Map<String, Map<String,Boolean>> groupMembers = new LinkedHashMap<>();
        String memberInfos[] = members.split("\\r?\\n");
        if (memberInfos.length == 0) {
            return groupMembers;
        }
        Set<String> signSet = new HashSet<>();
        List<Member> signMembers = membersMapper.selectByActivityId(activityId);
        for (Member signMember : signMembers) {
            signSet.add(signMember.getMemberName());
        }

        for (String memberInfo : memberInfos) {
            String[] result = memberInfo.split(",");
            if (result.length != 4) {
                return groupMembers;
            }
            String memberName = result[0]; // 用户名称
            String groupIdentifier = result[3]; // 所属分组

            // 格式化数据
            if (groupMembers.containsKey(groupIdentifier)) {
                if(signSet.contains(memberName)){
                    groupMembers.get(groupIdentifier).put(memberName,true);
                }else {
                    groupMembers.get(groupIdentifier).put(memberName,false);
                }
            } else {
                groupMembers.put(groupIdentifier, new LinkedHashMap<>());
                if(signSet.contains(memberName)){
                    groupMembers.get(groupIdentifier).put(memberName,true);
                }else {
                    groupMembers.get(groupIdentifier).put(memberName,false);
                }
            }
        }
        return groupMembers;
    }

    /**
     * 报名活动
     *
     * @return API response json
     */
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

                // 更新用户的名称
                User user = usersMapper.getByOpenId(LoginContext.getOpenId());
                if (user != null && StringUtils.isEmpty(user.getMemberName())) {
                    user.setMemberName(memberName);
                    userService.save(user);
                }

                break;
            }
        }
        return ApiResponse.ok();
    }

    public static void main(String[] args) {
        System.out.println(new BigDecimal((double)2.111).setScale(2,BigDecimal.ROUND_HALF_DOWN).toString());
    }
}
