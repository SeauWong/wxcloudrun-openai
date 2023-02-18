package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.param.MsgParam;
import com.tencent.wxcloudrun.service.DallEService;
import com.tencent.wxcloudrun.wrapper.DallEApiWrapper;
import com.tencent.wxcloudrun.wrapper.WechatSendMsgWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class DallEServiceImpl implements DallEService {

    @Resource
    private DallEApiWrapper dallEApiWrapper;
    @Resource
    private WechatSendMsgWrapper wechatSendMsgWrapper;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @Override
    public void handleMsg(MsgParam param) {
        if(!"text".equals(param.getMsgType())){
            return;
        }
        executorService.submit(() -> {
            String generations = dallEApiWrapper.generations(param.getContent());
            if(StringUtils.isNotBlank(generations)){
                wechatSendMsgWrapper.sendCustomMsg(param.getFromUserName(), generations);
            }
        });
    }
}
