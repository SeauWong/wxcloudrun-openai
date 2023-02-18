package com.tencent.wxcloudrun.controller;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.param.MsgParam;
import com.tencent.wxcloudrun.service.DallEService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dalle")
@Slf4j
public class DallEController {

    @Resource
    private DallEService dallEService;

    @PostMapping("/handleMsgs")
    public String handleMsg(@RequestBody String body) {
        log.info("body={}", body);
        MsgParam param = JSON.parseObject(body, MsgParam.class);
        if (!param.getContent().startsWith("作画")) {
            return null;
        }
        if (!"text".equals(param.getMsgType())) {
            return null;
        }

        try {
            String resMsg = dallEService.handleMsg(param);
            MsgParam result = new MsgParam();
            result.setContent(resMsg);
            result.setCreateTime(System.currentTimeMillis());
            result.setFromUserName(param.getToUserName());
            result.setToUserName(param.getFromUserName());
            result.setMsgType("text");
            return JSON.toJSONString(result);
        } catch (Exception e) {
            log.error("dallEService.handleMsg.fail", e);
            return null;
        }

    }

}
