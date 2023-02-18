package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.CallRecord;
import com.tencent.wxcloudrun.model.CallRecordExt;
import com.tencent.wxcloudrun.param.MsgParam;
import com.tencent.wxcloudrun.service.CallRecordService;
import com.tencent.wxcloudrun.service.DallEService;
import com.tencent.wxcloudrun.util.Md5Util;
import com.tencent.wxcloudrun.wrapper.DallEApiWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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
        if(rs.isPresent()){
            CallRecordExt ext = rs.map(CallRecord::getExtInfo).orElse(new CallRecordExt());
            if(Boolean.FALSE.equals(ext.getDallESuccess())){
                return "处理失败, 请更换描述后重试";
            }
            String url = ext.getUrl();
            if (StringUtils.isNotBlank(url)) {
                return url;
            }
        }


        CallRecord callRecord = rs.orElseGet(() -> callRecordService.save(toCallRecord(param)));

        executorService.submit(() -> {
                    String picUrl = dallEApiWrapper.generations(param.getContent());
                    CallRecordExt extInfo = callRecord.getExtInfo();
                    extInfo.setUrl(picUrl);
                    extInfo.setDallESuccess(null != picUrl);
                    callRecordService.save(callRecord);
                }
        );

        return "正在处理...10秒后重新发送获取";
    }

    private CallRecord toCallRecord(MsgParam param) {
        CallRecord callRecord = new CallRecord();
        callRecord.setBizType(DALLE);
        callRecord.setFromUser(param.getFromUserName());
        callRecord.setContentMd5(Md5Util.encode(param.getContent()));
        CallRecordExt ext = new CallRecordExt();
        ext.setContent(param.getContent());
        callRecord.setExtInfo(ext);
        return callRecord;
    }
}
