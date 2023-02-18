package com.tencent.wxcloudrun.wrapper;

import com.tencent.wxcloudrun.wrapper.model.Images;

public interface DallEApiWrapper {

    Images generations(String prompt, String format);
}
