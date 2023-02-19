package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.constants.DallEConstants;
import com.tencent.wxcloudrun.model.CallRecord;
import com.tencent.wxcloudrun.model.CallRecordExt;
import com.tencent.wxcloudrun.param.MsgParam;
import com.tencent.wxcloudrun.service.CallRecordService;
import com.tencent.wxcloudrun.service.GptService;
import com.tencent.wxcloudrun.util.Md5Util;
import com.tencent.wxcloudrun.wrapper.GptApiWrapper;
import com.tencent.wxcloudrun.wrapper.model.Completions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GptServiceImpl implements GptService {

    @Resource
    private GptApiWrapper gptApiWrapper;
    @Resource
    private CallRecordService callRecordService;

    private static final ExecutorService executorService = new ThreadPoolExecutor(8, 8,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1));

    private static final String GPT = "GPT";


    @Override
    public String handleMsg(MsgParam param) {

        //已存在记录，且url存在
        Optional<CallRecord> rs = callRecordService.findByUserAndContent(param.getFromUserName(), param.getContent());
        if (rs.isPresent()) {
            CallRecordExt ext = rs.map(CallRecord::getExtInfo).orElse(new CallRecordExt());
            String gptContent = ext.getGptContent();
            if (StringUtils.isNotBlank(gptContent)) {
                return gptContent;
            }
        }

        Long createTimestamp = rs.map(CallRecord::getGmtCreated).map(Date::getTime).orElse(0L);
        if(System.currentTimeMillis() - createTimestamp <= DallEConstants.HANDLE_TIMEOUT_S){
            return "我在处理啦...请等等哦";
        }

        executorService.submit(() -> asyncHandle(param, rs));

        return "正在处理...10秒后重新发送获取";
    }

    private void asyncHandle(MsgParam param, Optional<CallRecord> rs) {
        try {
            CallRecord callRecord = rs.orElseGet(() -> callRecordService.save(toCallRecord(param)));

            Completions completions = gptApiWrapper.completions(param.getContent());
            String gptContent = Optional.ofNullable(completions)
                    .map(Completions::getChoices)
                    .map(r -> r.get(0))
                    .map(Completions.Choice::getText)
                    .orElse(null);


            CallRecordExt extInfo = callRecord.getExtInfo();
            extInfo.setGptContent(gptContent);
            callRecordService.save(callRecord);

        } catch (Exception e) {
            log.error("GptServiceImpl_asyncHandle_fail", e);
        }

    }

    private CallRecord toCallRecord(MsgParam param) {
        CallRecord callRecord = new CallRecord();
        callRecord.setBizType(GPT);
        callRecord.setFromUser(param.getFromUserName());
        callRecord.setContentMd5(Md5Util.encode(param.getContent()));
        CallRecordExt ext = new CallRecordExt();
        ext.setContent(param.getContent());
        callRecord.setExtInfo(ext);
        return callRecord;
    }
}
