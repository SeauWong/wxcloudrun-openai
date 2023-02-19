package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.param.MsgParam;

public interface GptService {

    String handleMsg(MsgParam param);
}
