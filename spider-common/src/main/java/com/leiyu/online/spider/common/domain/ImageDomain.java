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
public class ImageDomain implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private Long sourceId;

    @Getter @Setter private String url;

    @Getter @Setter private String resourceName;

    @Getter @Setter private Integer no;

    @Getter @Setter private Long size;

    @Getter @Setter private Date updateTime;

    /**
     * 状态：1：初始，2：失败，3：成功
     */
    @Getter @Setter private Integer status;

    @Getter @Setter private String resourceType;

    @Getter @Setter private String dir;
}
