package com.tencent.wxcloudrun.wrapper;

public interface WechatSendMsgWrapper {

    /**
     * 主动回复消息
     * @param toUser
     * @param content
     */
    void sendCustomMsg(String toUser, String content);
}
