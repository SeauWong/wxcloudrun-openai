package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 会话记录
 * @author WongTheo
 * @date 2023-02-18
 */
@Data
public class CallRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * gmt_created
     */
    private Date gmtCreated;

    /**
     * gmt_modified
     */
    private Date gmtModified;

    /**
     * 消息业务类型
     */
    private String bizType;

    /**
     * from_user
     */
    private String fromUser;

    /**
     * content_md5
     */
    private String contentMd5;

    /**
     * ext
     */
    private CallRecordExt extInfo;

}