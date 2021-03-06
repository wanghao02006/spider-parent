package com.leiyu.online.spider.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "spider_images")
@ToString
@Setter
@Getter
public class ImageDomain implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long sourceId;
    private String url;
    private String resourceName;
    private Integer no;
    private Long size;
    private Date updateTime;

    /**
     * 状态：1：初始，2：失败，3：成功
     */
    private Integer status;

    private String resourceType;

    private String dir;

    private boolean isDelete;

    private Integer retryCount;
}
