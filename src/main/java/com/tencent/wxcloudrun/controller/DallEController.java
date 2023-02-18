package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.param.MsgParam;
import com.tencent.wxcloudrun.service.DallEService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/dalle")
@Slf4j
public class DallEController {

    @Resource
    private DallEService dallEService;

    @PostMapping("/handleMsgs")
    public ApiResponse handleMsg(HttpServletRequest request, MsgParam param){
        log.info("request={}", JSON.toJSONString(request, SerializerFeature.IgnoreErrorGetter));
        log.info("param={}", param);
        dallEService.handleMsg(param);
        return ApiResponse.ok();
    }


}
