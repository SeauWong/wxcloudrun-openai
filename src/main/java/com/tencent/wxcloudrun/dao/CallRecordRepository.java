package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.CallRecordDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description 会话记录
 * @author WongTheo
 * @date 2023-02-18
 */
@Repository
public interface CallRecordRepository extends JpaRepository<CallRecordDO,Long> {



}