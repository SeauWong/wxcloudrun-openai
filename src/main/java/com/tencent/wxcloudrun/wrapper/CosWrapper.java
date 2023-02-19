package com.tencent.wxcloudrun.wrapper;


public interface CosWrapper {

    String getPicUrl(String key);

    String upload(String base64Image);
}
