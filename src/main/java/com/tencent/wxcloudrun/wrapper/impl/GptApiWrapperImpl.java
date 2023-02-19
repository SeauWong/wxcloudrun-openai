package com.tencent.wxcloudrun.wrapper.impl;

import com.alibaba.fastjson2.JSON;
import com.tencent.wxcloudrun.wrapper.GptApiWrapper;
import com.tencent.wxcloudrun.wrapper.model.Completions;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class GptApiWrapperImpl implements GptApiWrapper {

    @Value("${gptkey}")
    private String gptkey;

    private static final String API_URL = "https://api.openai.com/v1/completions";

    @Override
    public Completions completions(String prompt) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + gptkey);

        Map<String, Object> params = new HashMap<>();
        params.put("model", "text-davinci-003");
        params.put("prompt", prompt);
        params.put("max_tokens", 2048);
        try {
            StringEntity formEntity = new StringEntity(JSON.toJSONString(params));
            httpPost.setEntity(formEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            String jsonStr = EntityUtils.toString(responseEntity);
            log.info("gpt response={}", jsonStr);
            Completions res = JSON.parseObject(jsonStr, Completions.class);
            return res;
        } catch (Exception e) {
            log.error("GptApiWrapperImpl.completions.fail", e);
            return null;
        }
    }

    public static void main(String[] args) {
        GptApiWrapperImpl service = new GptApiWrapperImpl();
        for(int i = 1; i<= 1; i++){
            Completions completions = service.completions("so happy");
            System.out.println(completions.getChoices().get(0).getText());
        }
        System.out.println(System.getenv());
    }
}
