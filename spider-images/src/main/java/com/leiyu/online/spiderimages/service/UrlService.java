package com.leiyu.online.spiderimages.service;

import com.leiyu.online.spider.common.domain.UrlDomain;

import java.util.List;

/**
 * Url服务类-主要用于与数据库交互
 */
public interface UrlService {

    /**
     * 查询根级网址
     * @return
     */
    List<UrlDomain> selectRootUrls();

    /**
     * 新增网址
     * @return
     */
    boolean insertOneUrl();

    /**
     * 查询满足条件的Url信息
     * @param urlDomain
     * @return
     */
    List<UrlDomain> selectUrlsByConditions(UrlDomain urlDomain);
}
