package com.tencent.wxcloudrun.wrapper.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Images {
    private String url;

    @JSONField(name = "b64_json")
    private String b64Json;
}
