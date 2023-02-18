package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.CallRecordRepository;
import com.tencent.wxcloudrun.model.CallRecord;
import com.tencent.wxcloudrun.model.CallRecordDO;
import com.tencent.wxcloudrun.service.CallRecordService;
import com.tencent.wxcloudrun.service.convertor.CallRecordConvertor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@Slf4j
public class CallRecordServiceImpl implements CallRecordService {

    @Resource
    private CallRecordRepository callRecordRepository;

    @Override
    public Optional<CallRecord> findById(Long id) {
        if(null == id){
            return Optional.empty();
        }
        return callRecordRepository.findById(id)
                .map(CallRecordConvertor::toDto);
    }

    @Override
    public Optional<CallRecord> findByUserAndContent(String fromUser, String content) {
        if(StringUtils.isAnyBlank(fromUser, content)){
            return Optional.empty();
        }
        String md5Crypt = Md5Crypt.md5Crypt(content.getBytes(StandardCharsets.UTF_8));
        CallRecordDO example = new CallRecordDO();
        example.setContentMd5(md5Crypt);
        example.setFromUser(fromUser);
        return callRecordRepository.findOne(Example.of(example))
                .map(CallRecordConvertor::toDto);
    }

    @Override
    public CallRecord save(CallRecord callRecord) {
        if(null == callRecord){
            return null;
        }
        CallRecordDO callRecordDO = callRecordRepository.save(CallRecordConvertor.toDO(callRecord));
        log.info("callRecordRepository.save.res={}", callRecordDO);
        return CallRecordConvertor.toDto(callRecordDO);
    }

}
