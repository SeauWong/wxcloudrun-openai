package com.tencent.wxcloudrun.model;

import lombok.Data;

@Data
public class CallRecordExt {

    private String url;

    private String content;

    private Boolean dallESuccess;

    private String gptContent;
}
