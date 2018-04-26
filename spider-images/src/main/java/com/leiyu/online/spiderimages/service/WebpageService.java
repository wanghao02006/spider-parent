package com.leiyu.online.spiderimages.service;

import com.leiyu.online.spider.common.domain.UrlDomain;

/**
 * 图片网页处理Service
 */
public interface WebpageService {

    /**
     * 解析网页
     * @param urlDomain
     */
    void analysisWebPage(UrlDomain urlDomain);
}
