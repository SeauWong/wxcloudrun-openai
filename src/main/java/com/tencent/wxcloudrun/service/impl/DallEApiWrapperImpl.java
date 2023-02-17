package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSON;
import com.tencent.wxcloudrun.controller.CounterController;
import com.tencent.wxcloudrun.service.DallEApiWrapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DallEApiWrapperImpl implements DallEApiWrapper {

    private static final Logger log = LoggerFactory.getLogger(CounterController .class);

    private static final String API_URL = "https://api.openai.com/v1/images/generations";

    @Value("${openai.appKey}")
    private String appKey;

    @Override
    public String generations(String prompt) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(API_URL);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + appKey);

        Map<String, String> params = new HashMap<>();
        params.put("prompt", "an apple");
        params.put("size", "512x512");
        params.put("response_format", "url");
        try {
            StringEntity formEntity = new StringEntity(JSON.toJSONString(params));
            httpPost.setEntity(formEntity);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        }catch (Exception e){
            log.error("DallEApiWrapperImpl_generations_fail", e);
            return null;
        }
    }
}
