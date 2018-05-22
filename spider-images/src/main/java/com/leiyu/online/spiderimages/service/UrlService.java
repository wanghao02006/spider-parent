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
    boolean insertOneUrl(UrlDomain urlDomain);

    /**
     * 查询满足条件的Url信息
     * @param urlDomain
     * @return
     */
    List<UrlDomain> selectUrlsByConditions(UrlDomain urlDomain);

    /**
     * 判断路径是否存在
     * @param url
     * @return
     */
    boolean isExists(String url);

    /**
     * 批量更新
     * @param domains
     * @return
     */
    boolean batchUpdateUrlStatus(List<UrlDomain> domains);
}
