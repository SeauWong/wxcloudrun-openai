package com.tencent.wxcloudrun.param;

import lombok.Data;

@Data
public class MsgParam {


    private String toUserName;

    private String fromUserName;

    private Long createTime;

    private String msgType;

    private String content;

    private long msgId;

}
