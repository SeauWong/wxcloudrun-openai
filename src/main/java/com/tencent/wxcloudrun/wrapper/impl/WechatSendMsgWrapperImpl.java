package com.tencent.wxcloudrun.wrapper.impl;

import com.alibaba.fastjson2.JSON;
import com.tencent.wxcloudrun.wrapper.WechatSendMsgWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class WechatSendMsgWrapperImpl implements WechatSendMsgWrapper {
    private static final String CUSTOM_SEND_URL = "http://api.weixin.qq.com/cgi-bin/message/custom/send";
    @Override
    public void sendCustomMsg(String toUser, String content) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(CUSTOM_SEND_URL);

        Map<String, Object> params = new HashMap<>();
        params.put("touser", toUser);
        params.put("msgtype", "text");
        Map<String, Object> text = new HashMap<>();
        text.put("content", content);
        params.put("text", text);
        try {
            StringEntity formEntity = new StringEntity(JSON.toJSONString(params));
            httpPost.setEntity(formEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String res = EntityUtils.toString(responseEntity);
            log.info("WechatSendMsgWrapperImpl_sendCustomMsg_finish, res={}",res);
        }catch (Exception e){
            log.error("WechatSendMsgWrapperImpl_sendCustomMsg_fail", e);
        }
    }
}
