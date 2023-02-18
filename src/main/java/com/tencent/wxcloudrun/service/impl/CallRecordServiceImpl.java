package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.repository.CallRecordRepository;
import com.tencent.wxcloudrun.model.CallRecord;
import com.tencent.wxcloudrun.model.CallRecordDO;
import com.tencent.wxcloudrun.service.CallRecordService;
import com.tencent.wxcloudrun.service.convertor.CallRecordConvertor;
import com.tencent.wxcloudrun.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
        CallRecordDO example = new CallRecordDO();
        example.setContentMd5(Md5Util.encode(content));
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

    public static void main(String[] args) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("123".getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        System.out.println(myHash);
    }

}
