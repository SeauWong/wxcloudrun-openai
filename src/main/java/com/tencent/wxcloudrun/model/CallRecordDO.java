package com.tencent.wxcloudrun.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description 会话记录
 * @author WongTheo
 * @date 2023-02-18
 */
@Entity
@Data
@Table(name="call_record")
public class CallRecordDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * id
     */
    @Column(name="id")
    private Long id;

    /**
     * gmt_created
     */
    @Column(name="gmt_created")
    private Date gmtCreated;

    /**
     * gmt_modified
     */
    @Column(name="gmt_modified")
    private Date gmtModified;

    /**
     * 消息业务类型
     */
    @Column(name="biz_type")
    private String bizType;

    /**
     * from_user
     */
    @Column(name="from_user")
    private String fromUser;

    /**
     * content_md5
     */
    @Column(name="content_md5")
    private String contentMd5;

    /**
     * ext
     */
    @Column(name="ext")
    private String ext;

    public CallRecordDO() {
    }

}