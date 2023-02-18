package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.CallRecord;

import java.util.Optional;

public interface CallRecordService {

    Optional<CallRecord> findById(Long id);

    Optional<CallRecord> findByUserAndContent(String fromUser, String content);

    CallRecord save(CallRecord callRecord);
}
