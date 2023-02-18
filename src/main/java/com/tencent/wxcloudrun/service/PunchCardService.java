package com.tencent.wxcloudrun.service;

import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.dao.MembersMapper;
import com.tencent.wxcloudrun.dao.PunchCardMapper;
import com.tencent.wxcloudrun.dao.RewardMapper;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.dto.PunchCardQuery;
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


    public ApiResponse delete(Long id){
        Record record = punchCardMapper.selectByPrimaryKey(id);
        if (null!=record  && !record.getMemberOpenId().equals(LoginContext.getOpenId())) {
            ApiResponse.error("OPEN_ID_NOT_MATCH", "您没有权限删除");
        }

        punchCardMapper.deleteByPrimaryKey(id);

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
            if (punchCardTimeDate.getDay() < LocalDateTime.now().getDayOfMonth()) {
                if (activity.getCanRepunchCard() == Activity.IS_SUPPORT_REPUNCHCRD) {

                    // 补卡次数判断
                    if (activity.getRepunchCardDays() < punchCardMapper.getRepunchCount(activityId, LoginContext.getOpenId())) {
                        record.setIsRepunchCard(Record.IS_REPUNCHCRD);
                    } else {
                        ApiResponse.error("REACH_REPUNCHCARD_LIMIT", "达到补卡上限");
                    }

                } else {
                    ApiResponse.error("NOT_SUPPORT_REPUNCHCARD", "活动不支持补卡");
                }
            }
            // 设置打卡日期
            record.setPunchCardTime(punchCardTime);
            punchCardMapper.insert(record);
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

        List<Reward> bestRecords = rewardMapper.getByRecordId(recordId, LoginContext.getOpenId(), Reward.REWARD_TYPE_BEST);
        if (!CollectionUtils.isEmpty(bestRecords)) {
            punchCardDTO.setBest(true); // 优选

        }


        List<Reward> thumbsupRecords = rewardMapper.getByRecordId(recordId, LoginContext.getOpenId(), Reward.REWARD_TYPE_THUMBS_UP);
        punchCardDTO.setThumbsUp(thumbsupRecords.size()); // 点赞数
        for (Reward thumbsupRecord : thumbsupRecords) {
            if (thumbsupRecord.getGiveRewardUserId().equals(LoginContext.getOpenId())){
                punchCardDTO.setUserThumbsup(true);
            }
        }

        List<Reward> levelRecords = rewardMapper.getByRecordId(recordId, LoginContext.getOpenId(), Reward.REWARD_TYPE_LEVE);
        if (!CollectionUtils.isEmpty(levelRecords)) {
            Integer level = levelRecords.get(0).getRewardLevel();
            punchCardDTO.setLevel(level != null ? level : 0); // 等级
        }
        punchCardDTO.setRecordId(recordId);

        Activity activity = activityMapper.selectByPrimaryKey(record.getActivityId());
        punchCardDTO.setPunchCardType(activity.getPunchCardType());

        User user = userService.getUser(LoginContext.getOpenId());
        punchCardDTO.setAvtar(user.getAvator());
        punchCardDTO.setActivityId(record.getActivityId());

        punchCardDTO.setComments(rewardService.getComments(recordId)); // 评论列表
        return punchCardDTO;
    }

}
