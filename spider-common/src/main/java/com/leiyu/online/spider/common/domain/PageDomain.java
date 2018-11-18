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
@Setter
@Getter
@ToString
public class PageDomain implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long parentId;

    private String url;

    private String urlName;
    private String baseUrl;
    private Integer level;
    private Boolean isParent;
    private Date updateTime;
    private Boolean hasDownload;
    /**
     * 状态：1：初始，2：失败，3：成功
     */
    private Integer status;

    private String resourceType;
    private String dir;
    private String handleType;
}
