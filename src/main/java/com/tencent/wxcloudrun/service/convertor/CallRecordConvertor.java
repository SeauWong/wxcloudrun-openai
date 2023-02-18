package com.tencent.wxcloudrun.service.convertor;

import com.alibaba.fastjson.JSON;
import com.tencent.wxcloudrun.model.CallRecord;
import com.tencent.wxcloudrun.model.CallRecordDO;
import com.tencent.wxcloudrun.model.CallRecordExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

public class CallRecordConvertor {

    public static CallRecord toDto(CallRecordDO source){
        if(null == source){
            return null;
        }
        CallRecord target = new CallRecord();
        BeanUtils.copyProperties(source, target);
        if(StringUtils.isNotBlank(source.getExt())){
            target.setExtInfo(JSON.parseObject(source.getExt(), CallRecordExt.class));
        }
        return target;
    }

    public static CallRecordDO toDO(CallRecord source){
        if(null == source){
            return null;
        }
        CallRecordDO target = new CallRecordDO();
        BeanUtils.copyProperties(source, target);
        target.setExt(JSON.toJSONString(source.getExtInfo()));
        return target;
    }
}
