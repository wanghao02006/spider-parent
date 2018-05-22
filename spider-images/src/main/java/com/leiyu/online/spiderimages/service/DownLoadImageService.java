package com.leiyu.online.spiderimages.service;

import com.leiyu.online.spider.common.domain.ImageDomain;

public interface DownLoadImageService {

    /**
     * 下载图片
     * @param imageDomain
     * @return
     */
    String downLoadImage(ImageDomain imageDomain);
}
