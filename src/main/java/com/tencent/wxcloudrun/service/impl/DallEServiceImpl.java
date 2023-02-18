package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.CallRecord;
import com.tencent.wxcloudrun.model.CallRecordExt;
import com.tencent.wxcloudrun.param.MsgParam;
import com.tencent.wxcloudrun.service.CallRecordService;
import com.tencent.wxcloudrun.service.DallEService;
import com.tencent.wxcloudrun.wrapper.DallEApiWrapper;
import com.tencent.wxcloudrun.wrapper.WechatSendMsgWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class DallEServiceImpl implements DallEService {

    @Resource
    private DallEApiWrapper dallEApiWrapper;
    @Resource
    private CallRecordService callRecordService;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    private static final String DALLE = "DALLE";

    @Override
    public String handleMsg(MsgParam param) {

        //已存在记录，且url存在
        Optional<CallRecord> rs = callRecordService.findByUserAndContent(param.getFromUserName(), param.getContent());
        String url = rs.map(CallRecord::getExtInfo)
                .map(CallRecordExt::getUrl)
                .orElse(null);
        if(StringUtils.isNotBlank(url)){
            return url;
        }

        CallRecord callRecord = callRecordService.save(toCallRecord(param));

        executorService.submit(() -> {
            String picUrl = dallEApiWrapper.generations(param.getContent());
            if (StringUtils.isNotBlank(picUrl)) {
                callRecord.getExtInfo().setUrl(picUrl);
                callRecordService.save(callRecord);
            }
        });

        return "已下发, 请稍后复制消息重新发送获取";
    }

    private CallRecord toCallRecord(MsgParam param) {
        CallRecord callRecord = new CallRecord();
        callRecord.setBizType(DALLE);
        callRecord.setFromUser(param.getFromUserName());
        callRecord.setContentMd5(Md5Crypt.md5Crypt(param.getContent().getBytes(StandardCharsets.UTF_8)));
        CallRecordExt ext = new CallRecordExt();
        ext.setContent(param.getContent());
        callRecord.setExtInfo(ext);
        return callRecord;
    }
}
