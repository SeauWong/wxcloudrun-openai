package com.tencent.wxcloudrun.wrapper;

import com.tencent.wxcloudrun.wrapper.model.Completions;

public interface GptApiWrapper {

    Completions completions(String prompt);
}
