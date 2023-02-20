package com.tencent.wxcloudrun.adaptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.wxcloudrun.constants.CommonConstants;
import com.tencent.wxcloudrun.constants.HttpInterfaceUrl;
import com.tencent.wxcloudrun.dto.MessageRequest;
import com.tencent.wxcloudrun.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 消息发送适配器
 *
 * @Author：zhoutao
 * @Date：2023/2/18 14:31
 */
@Slf4j
@Component
public class MessageSendAdaptor {

    /**
     * 消息发送接口
     * @param messageRequest
     * @param accessToken
     */
    public void send(MessageRequest messageRequest, String accessToken) {
        try {
            StringBuilder url = new StringBuilder(HttpInterfaceUrl.message);
            if(StringUtil.isNotEmpty(accessToken)){
                url.append("?").append(accessToken);
            }
            HttpPost httpPost = HttpUtil.httpPost(url.toString(), JSON.toJSONString(messageRequest));
            String result = HttpUtil.httpPost(httpPost);
            if(result!= null ){
               JSONObject resultObject = JSONObject.parseObject(result)   ;
                Integer errorCode = resultObject.getInteger(CommonConstants.ERROR_CODE);
                if(errorCode== null || errorCode != CommonConstants.ERROR_CODE_SUCCESS){
                    log.error("message send error, result:{},messageRequest:{},accessToken:{}",result,messageRequest,accessToken);
               }
            }
        } catch (Exception e) {
            log.error("message send error,messageRequest:{},accessToken:{}", messageRequest,accessToken, e);
        }
    }
}
