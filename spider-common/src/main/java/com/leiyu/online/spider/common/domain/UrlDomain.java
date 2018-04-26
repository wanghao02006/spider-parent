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

@Table(name = "spider_url")
@ToString
public class UrlDomain implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private Long parentId;

    @Getter @Setter private String url;

    @Getter @Setter private String urlName;

    @Getter @Setter private String baseUrl;

    @Getter @Setter private Integer level;

    @Getter @Setter private Boolean isParent;

    @Getter @Setter private Date updateTime;

    @Getter @Setter private Boolean hasDownload;

    /**
     * 状态：1：初始，2：失败，3：成功
     */
    @Getter @Setter private Integer status;

    @Getter @Setter private String resourceType;
}
