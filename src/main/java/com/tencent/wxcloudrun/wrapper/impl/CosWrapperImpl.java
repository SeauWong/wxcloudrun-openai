package com.tencent.wxcloudrun.wrapper.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.tencent.wxcloudrun.wrapper.CosWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.util.UUID;

@Component
@Slf4j
public class CosWrapperImpl implements CosWrapper {

    @Value("${cos.bucket:0}")
    private String bucket;

    @Resource
    private COSClient cosClient;

    @Override
    public String getPicUrl(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        return String.format("https://%s.tcb.qcloud.la/%s", bucket, key);
    }

    @Override
    public String upload(String base64Image) {
        if(StringUtils.isBlank(base64Image)){
            return null;
        }
        String fileName = UUID.randomUUID().toString().replaceAll("-", "");

        byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);

        // 指定文件将要存放的存储桶
        String bucketName = bucket;
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为 folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = "dalle/" + fileName + ".jpg";
        cosClient.putObject(bucketName, key, new ByteArrayInputStream(imageBytes), new ObjectMetadata());

        return key;
    }
}
