package com.tencent.wxcloudrun.service;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CoachEnum;
import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
import com.tencent.wxcloudrun.dto.RewardQuery;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.util.DateUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 打卡业务逻辑处理
 *
 * @Author：zhoutao
 * @Date：2023/1/26 19:58
 */
@Service
public class PunchCardService {

    @Resource
    PunchCardMapper punchCardMapper;

    @Resource
    UserService userService;

    @Resource
    RewardService rewardService;

    @Resource
    ActivityMapper activityMapper;

    @Resource
    MembersMapper membersMapper;

    @Resource
    RewardMapper rewardMapper;

    @Resource
    CommentMapper commentMapper;


    public ApiResponse delete(Long id){
        Record record = punchCardMapper.selectByPrimaryKey(id);
        if (null!=record  && !record.getMemberOpenId().equals(LoginContext.getOpenId())) {
            return  ApiResponse.error("OPEN_ID_NOT_MATCH", "您没有权限删除");
        }
        punchCardMapper.deleteByPrimaryKey(id);

        // 取消点赞积分
        rewardMapper.deleteByPunchCardId(id);

        // 删除评论信息
        commentMapper.deleteByPunchCardId(id);

        return ApiResponse.ok();
    }

    /**
     * 活动打卡服务
     *
     * @return API response json
     */
    public ApiResponse punchcard(Long id, String content
            , long activityId, String punchCardTime) {

        // 新提交打卡的校验
        if (id == null) {
            // 判断用户在某一天是否打过卡
            PunchCardQuery query = new PunchCardQuery();
            query.setPunchCardTime(punchCardTime);
            query.setActivityId(activityId);
            query.setOpenId(LoginContext.getOpenId());
            if (punchCardMapper.count(query) > 0) {
                return ApiResponse.error("USER_ALREAD_PUNCH_CARD", "您已经打过卡，请勿重复打卡");
            }

            Activity activity = activityMapper.selectByPrimaryKey(activityId);

            Record record = new Record();
            record.setId(id);
            record.setContent(content);
            record.setMemberOpenId(LoginContext.getOpenId());
            record.setActivityId(activityId);
            record.setTeamCode(activity.getTeamCode());

            Member member = membersMapper.selectByOpenId(LoginContext.getOpenId()
                    , activityId);
            record.setGroupIdentifier(member.getGroupIdentifier()); // 用户分组

            // 补卡逻辑，检查当前打卡日期
            Date punchCardTimeDate = DateUtil.getymdStr2SDate(punchCardTime);
            if(punchCardTimeDate.before(DateUtil.asDate(activity.getActivityStartTime()))){
                return ApiResponse.error("PUNCHCARD_TIME_BEFORE_START_TIME","打卡时间早于活动开始时间！");
            }
            if (punchCardTimeDate.getDate() < LocalDateTime.now().getDayOfMonth()) {
                if (activity.getCanRepunchCard() == Activity.IS_SUPPORT_REPUNCHCRD) {

                    // 补卡次数判断
                    if (activity.getRepunchCardDays() > punchCardMapper.getRepunchCount(activityId, LoginContext.getOpenId())) {
                        record.setIsRepunchCard(Record.IS_REPUNCHCRD);
                    } else {
                      return   ApiResponse.error("REACH_REPUNCHCARD_LIMIT", "达到补卡上限");
                    }

                } else {
                   return ApiResponse.error("NOT_SUPPORT_REPUNCHCARD", "活动不支持补卡");
                }
            }
            // 设置打卡日期
            record.setPunchCardTime(punchCardTime);
            punchCardMapper.insert(record);

            // 添加打卡积分
            rewardService.reward(record.getId(),Reward.REWARD_TYPE_PUNCH_CARD,null);
            return ApiResponse.ok(record.getId());
        }
        // 更新打卡记录
        else {
            Record record = punchCardMapper.selectByPrimaryKey(id);
            record.setContent(content);
            punchCardMapper.updateByPrimaryKey(record);
            return ApiResponse.ok(record.getId());
        }
    }

    public static void main(String[] args) {
        String punchCardTime = "2023-02-24";
        Date punchCardTimeDate = DateUtil.getymdStr2SDate(punchCardTime);
        System.out.println(punchCardTimeDate.getDate());
        System.out.println(LocalDateTime.now().getDayOfMonth());

        if (punchCardTimeDate.getDate() < LocalDateTime.now().getDayOfMonth()){
            System.out.println("wrong");
        }
    }

    public PunchCardDTO getRecord(String openId, String punchCardTime, Long activityId) {
        PunchCardQuery query = new PunchCardQuery();
        query.setPunchCardTime(punchCardTime);
        query.setActivityId(activityId);
        query.setOpenId(openId);
        Page<PunchCardDTO> page = query(query);
        if(page.getTotalRecords() >= 0){
            return page.getEntityList().get(0);
        }
        return null;
    }
    /**
     * 查询活动打卡
     *
     * @return API response json
     */
    public Page<PunchCardDTO> query(PunchCardQuery query) {
        Page<PunchCardDTO> page = new Page<>();
        int count = punchCardMapper.count(query);
        if (count > 0) {
            List<PunchCardDTO> punchCardDTOS = new ArrayList<>();
            List<Record> records = punchCardMapper.query(query);
            for (Record record : records) {
                punchCardDTOS.add(getPunchCardRecord(record.getId()));
            }
            page.setEntityList(punchCardDTOS);
        }
        page.setTotalRecords(count);
        return page;
    }

    /**
     * 查询打卡记录
     *
     * @return API response json
     */
    public PunchCardDTO getPunchCardRecord(long recordId) {
        Record record = punchCardMapper.selectByPrimaryKey(recordId);
        if (!record.getMemberOpenId().equals(LoginContext.getOpenId())) {
            ApiResponse.error("OPEN_ID_NOT_MATCH", "您没有权限");
        }

        PunchCardDTO punchCardDTO = new PunchCardDTO();
        punchCardDTO.setCreateAt(DateUtil.getDate2Str(DateUtil.asDate(record.getCreatedAt())));
        punchCardDTO.setPunchCardTime(record.getPunchCardTime());
        punchCardDTO.setContent(record.getContent());
        Member member = membersMapper.selectByOpenId(record.getMemberOpenId(), record.getActivityId());
        punchCardDTO.setUserName(member.getMemberName());
        punchCardDTO.setDeptName(member.getDeptName());
        punchCardDTO.setPositionName(member.getPositionName());
        punchCardDTO.setGroupIdentifier(member.getGroupIdentifier());

        // 是否为本人
        punchCardDTO.setCanEdit(LoginContext.getOpenId().equals(record.getMemberOpenId()));
        punchCardDTO.setCoach(CoachEnum.isCoach(LoginContext.getOpenId()));

        RewardQuery bestQuery = new RewardQuery();
        bestQuery.setRecordId(recordId);
        bestQuery.setType(Reward.REWARD_TYPE_BEST);
        if(rewardMapper.count(bestQuery) > 0){
            punchCardDTO.setBest(true); // 优选
        }

        RewardQuery thumbsupQuery = new RewardQuery();
        thumbsupQuery.setRecordId(recordId);
        thumbsupQuery.setType(Reward.REWARD_TYPE_THUMBS_UP);
        punchCardDTO.setThumbsUp(rewardMapper.count(thumbsupQuery)); // 总点赞数
        thumbsupQuery.setGiveRewardUserId(LoginContext.getOpenId()); // 当前用户是否点赞
        if(rewardMapper.count(thumbsupQuery) > 0){
            punchCardDTO.setUserThumbsup(true);
        }

        List<Reward> levelRecords = rewardMapper.getByRecordId(recordId, Reward.REWARD_TYPE_LEVE);
        if (!CollectionUtils.isEmpty(levelRecords)) {
            Integer level = levelRecords.get(0).getRewardLevel();
            punchCardDTO.setLevel(level != null ? level : 0); // 等级
        }
        punchCardDTO.setRecordId(recordId);

        Activity activity = activityMapper.selectByPrimaryKey(record.getActivityId());
        punchCardDTO.setPunchCardType(activity.getPunchCardType());

        User user = userService.getUser(record.getMemberOpenId());
        punchCardDTO.setAvtar(user.getAvator());
        punchCardDTO.setActivityId(record.getActivityId());

        punchCardDTO.setComments(rewardService.getComments(recordId)); // 评论列表
        return punchCardDTO;
    }

}
