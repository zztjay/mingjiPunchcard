package com.tencent.wxcloudrun.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.jsonzou.jmockdata.JMockData;
import com.tencent.wxcloudrun.common.LoginContext;
import com.tencent.wxcloudrun.common.Page;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.constants.CoachEnum;
import com.tencent.wxcloudrun.dao.*;
import com.tencent.wxcloudrun.dto.CommentDTO;
import com.tencent.wxcloudrun.dto.CommentQuery;
import com.tencent.wxcloudrun.dto.PunchCardDTO;
import com.tencent.wxcloudrun.model.*;
import com.tencent.wxcloudrun.model.Record;
import com.tencent.wxcloudrun.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * 评论和奖励业务处理器
 *
 * @Author：zhoutao
 * @Date：2023/1/17 16:37
 */
@Slf4j
@Service
public class RewardService {
    @Resource
    RewardMapper rewardMapper;

    @Resource
    MembersMapper membersMapper;

    @Resource
    PunchCardService punchCardService;

    @Resource
    ActivityMapper activityMapper;

    @Resource
    PunchCardMapper punchCardMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    UsersMapper usersMapper;

    /**
     * 活动打卡评论服务
     *
     * @return API response json
     */
    public ApiResponse comment(long punchCardId, // 打卡记录id
                        String rootCommentContentType, //  评论内容类型，full. 完整句，positive. 正向，inpositive.负向，iwant：我还想做什么，thoughts：感想
                        Long replyCommentId //  被回复的评论ID，评论为空 ,
                        , String content // 评论的内容
    ) {
        Comment comment = new Comment();
        Record record = punchCardMapper.selectByPrimaryKey(punchCardId);
        Member commentMember = membersMapper.selectByOpenId(LoginContext.getOpenId(), record.getActivityId());
        Activity activity = activityMapper.selectByPrimaryKey(record.getActivityId());

        User user = usersMapper.getByOpenId(LoginContext.getOpenId());
        comment.setAvatar(user.getAvator());
        comment.setPunchCardId(punchCardId);
        comment.setCommentUserId(LoginContext.getOpenId());
        comment.setCommentUserName(commentMember.getMemberName());
        if (activity.getCoachs().contains(LoginContext.getOpenId())) {
            comment.setCommentUserType(Reward.REWARD_USRE_TYPE_COACH);
        } else {
            comment.setCommentUserType(Reward.REWARD_USRE_TYPE_MEMBER);
        }
        comment.setContent(content);

        // 对内容的评论
        if (!StringUtil.isEmpty(rootCommentContentType) && (null == replyCommentId || replyCommentId <=0)) {
            comment.setRootCommentContentType(rootCommentContentType);
            Member receiveMember = membersMapper.selectByOpenId(record.getMemberOpenId(), record.getActivityId());
            User receiveUser = usersMapper.getByOpenId(record.getMemberOpenId());
            comment.setReceiveUserId(receiveMember.getMemberOpenId());
            comment.setReceiveUserName(receiveMember.getMemberName());
            comment.setReceiveUserAvator(receiveUser.getAvator());
            comment.setCommentUserType(Reward.REWARD_USRE_TYPE_MEMBER);
            comment.setReceiveUserType(Reward.REWARD_USRE_TYPE_MEMBER);

            // 写入内容
            if (rootCommentContentType.equals("full")) {
                comment.setRootCommentContent(record.getContent());
            } else {
                comment.setRootCommentContent(JSON.parseObject(record.getContent()).getString(rootCommentContentType));
            }

            comment.setType(Comment.COMMENT_TYPE_COMMENT);
        }
        // 对评论的回复
        else if (StringUtil.isEmpty(rootCommentContentType)  && null != replyCommentId ) {
            Comment replyComment = commentMapper.selectByPrimaryKey(replyCommentId);

            // 检查是否自己回复自己
            if(replyComment.getCommentUserId().equals(LoginContext.getOpenId())){
                return ApiResponse.error("USER_CANT_REPLY_SELF","用户不能自己回复自己");
            }

            comment.setRootCommentId(replyComment.getRootCommentId());
            comment.setReplyCommentId(replyCommentId); // 被评论的id
            comment.setReceiveUserId(replyComment.getCommentUserId()); // 被评论用户ID，就是上一条评论的评论者
            comment.setReceiveUserName(replyComment.getCommentUserName());// 被评论用户名称，就是上一条评论的评论者
            comment.setReceiveUserType(replyComment.getCommentUserType()); // 被评论用户的类型

            User receiveUser = usersMapper.getByOpenId(replyComment.getCommentUserId()); // 被评论用户的头像
            comment.setReceiveUserAvator(receiveUser.getAvator());

            comment.setRootCommentContent(replyComment.getRootCommentContent());
            comment.setRootCommentContentType(replyComment.getRootCommentContentType());
            comment.setType(Comment.COMMENT_TYPE_REPLY);
        }

        // 针对内容的评论，要写入rootCommentId
         commentMapper.insert(comment);
        if (!StringUtil.isEmpty(rootCommentContentType) && (null == replyCommentId || replyCommentId <= 0)) {
            comment.setRootCommentId(comment.getId());
            comment.setId(comment.getId());
            commentMapper.updateByPrimaryKey(comment);
        }

        return ApiResponse.ok(comment.getId());
    }

    public Page<List<CommentDTO>> getUserLastComments(CommentQuery query){
        Page<List<CommentDTO>> page = new Page<>();
        int total = commentMapper.countLatestComments(query);
        if( total > 0){
            List<Comment> rootComments = commentMapper.queryLatestComments(query);
            List<List<CommentDTO>> punchCardComments = buildReverse(rootComments);
            page.setEntityList(punchCardComments);
        }
        page.setTotalRecords(total);
        page.setCurrentPage(query.getCurrentPage());
        return page;
    }

    private List<List<CommentDTO>> buildReverse(List<Comment> lastComments){
        List<List<CommentDTO>> punchCardComments = new ArrayList<>();
        for (Comment lastComment : lastComments) {

            Record punchcard = punchCardMapper.selectByPrimaryKey(lastComment.getPunchCardId());

            List<CommentDTO> commentList = new ArrayList<>();
            List<Comment> comments = commentMapper.getCommentsReverse(lastComment.getRootCommentId(),lastComment.getId());
            for (Comment comment : comments) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setContent(comment.getContent());
                commentDTO.setId(comment.getId());
                commentDTO.setReceiveUserName(comment.getReceiveUserName());
                commentDTO.setReceiveUserAvator(comment.getReceiveUserAvator());
                commentDTO.setCommentUserName(comment.getCommentUserName());
                commentDTO.setAvatar(comment.getAvatar());
                commentDTO.setCreateAt(DateUtil.getDate2Str(DateUtil.asDate(comment.getCreatedAt())));
                commentDTO.setPunchCardId(comment.getPunchCardId());
                commentDTO.setRootCommentId(comment.getRootCommentId());
                commentDTO.setRootCommentContentType(comment.getRootCommentContentType());
                commentDTO.setRootCommentContent(comment.getRootCommentContent());
                commentDTO.setType(comment.getType());
                commentDTO.setPunchCardTime(DateUtil.getDate2yymmddStr(DateUtil.asDate(punchcard.getCreatedAt()))); // 打卡时间
                commentDTO.setCoach(CoachEnum.isCoach(comment.getCommentUserId())); // 是否为教练
                commentList.add(commentDTO);
            }

            punchCardComments.add(commentList);
        }
        return punchCardComments;
    }

    /**
     * 活动打卡评论查询服务，包含被评论的内容
     *
     * @return API response json
     */
    public  List<List<CommentDTO>> getComments(Long punchCardId) {
        List<Comment> rootComments = commentMapper.getRootComments(punchCardId);
        List<List<CommentDTO>> punchCardComments = build(rootComments);
        return punchCardComments;
    }

    private List<List<CommentDTO>> build(List<Comment> rootComments){
        List<List<CommentDTO>> punchCardComments = new ArrayList<>();
        for (Comment rootComment : rootComments) {

            Record punchcard = punchCardMapper.selectByPrimaryKey(rootComment.getPunchCardId());

            List<CommentDTO> commentList = new ArrayList<>();
            List<Comment> comments = commentMapper.getComments(rootComment.getRootCommentId());
            for (Comment comment : comments) {
                CommentDTO commentDTO = new CommentDTO();
                commentDTO.setContent(comment.getContent());
                commentDTO.setId(comment.getId());
                commentDTO.setReceiveUserName(comment.getReceiveUserName());
                commentDTO.setReceiveUserAvator(comment.getReceiveUserAvator());
                commentDTO.setCommentUserName(comment.getCommentUserName());
                commentDTO.setAvatar(comment.getAvatar());
                commentDTO.setCreateAt(DateUtil.getDate2Str(DateUtil.asDate(comment.getCreatedAt())));
                commentDTO.setPunchCardId(comment.getPunchCardId());
                commentDTO.setRootCommentId(comment.getRootCommentId());
                commentDTO.setRootCommentContentType(comment.getRootCommentContentType());
                commentDTO.setRootCommentContent(comment.getRootCommentContent());
                commentDTO.setType(comment.getType());
                commentDTO.setPunchCardTime(DateUtil.getDate2yymmddStr(DateUtil.asDate(punchcard.getCreatedAt()))); // 打卡时间
                commentDTO.setCoach(CoachEnum.isCoach(comment.getCommentUserId())); // 是否为教练
                commentList.add(commentDTO);
            }

            punchCardComments.add(commentList);
        }
        return punchCardComments;
    }

    /**
     * 活动打卡评分服务
     *
     * @return API response json
     */
    public   ApiResponse reward(long punchCardId,
                       int rewardType, int rewardLevel) {

        Reward reward = new Reward();
        reward.setRewardLevel(rewardLevel);
        reward.setRewardType(rewardType);
        reward.setPunchCardId(punchCardId);
        Record record = punchCardMapper.selectByPrimaryKey(punchCardId);
        reward.setUserOpenId(record.getMemberOpenId());

        // 补充其他信息
        reward.setGiveRewardUserId(LoginContext.getOpenId());

        Activity activity = activityMapper.selectByPrimaryKey(record.getActivityId());
        if (CoachEnum.isCoach(LoginContext.getOpenId())) {
            reward.setGiveRewardUserType(Reward.REWARD_USRE_TYPE_COACH);
        } else {
            reward.setGiveRewardUserType(Reward.REWARD_USRE_TYPE_MEMBER);
        }

        // 计算获得积分
        JSONArray rewardRules = JSONArray.parseArray(activity.getRewardRule());
        for (JSONObject rule : rewardRules.toJavaList(JSONObject.class)) {
            Date now = new Date();
            // 积分计算逻辑，评级需要3和4等级的才可以
            if(rule.getInteger("type") == rewardType && rule.getDate("startTime").before(now)){
                if(rewardType == Reward.REWARD_TYPE_LEVE && (rewardLevel == 3 || rewardLevel == 4)){
                    reward.setRewardPoint(rule.getInteger("basePoint"));
                } else {
                    reward.setRewardPoint(rule.getInteger("basePoint"));
                }
            }
        }
        rewardMapper.insert(reward);

        return ApiResponse.ok();
    }

}
