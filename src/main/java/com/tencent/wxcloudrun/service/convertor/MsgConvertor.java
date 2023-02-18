package com.tencent.wxcloudrun.service.convertor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PascalNameFilter;
import com.tencent.wxcloudrun.param.MsgParam;

public class MsgConvertor {

    public static String buildJson(String to, String from, String content){
        MsgParam param = new MsgParam();
        param.setMsgType("text");
        param.setCreateTime(System.currentTimeMillis() / 1000);
        param.setToUserName(to);
        param.setFromUserName(from);
        param.setContent(content);
        return JSON.toJSONString(param, new PascalNameFilter());
    }
}
