package com.tencent.wxcloudrun.wrapper.model;

import lombok.Data;

import java.util.List;

@Data
public class Response {

    private Long created;

    private List<Images> data;
}
