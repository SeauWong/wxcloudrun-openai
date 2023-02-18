package com.tencent.wxcloudrun.param;

import lombok.Data;

@Data
public class MsgParam {


    private String ToUserName;


    private String FromUserName;


    private Long CreateTime;


    private String MsgType;


    private String Content;


    private Long MsgId;

}
