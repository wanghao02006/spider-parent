package com.leiyu.online.spiderimages.service;

import com.leiyu.online.spider.common.domain.ImageDomain;

import java.util.List;

public interface ImageService {

    /**
     * 增加图片
     * @param imageDomain
     * @return
     */
    boolean insertOne(ImageDomain imageDomain);

    /**
     * 更新信息
     * @param imageDomain
     * @return
     */
    boolean updateOne(ImageDomain imageDomain);

    /**
     * 查询列表
     * @param imageDomain
     * @return
     */
    List<ImageDomain> selectImagesByCodition(ImageDomain imageDomain);


    /**
     * 判断图片是否存在
     * @param url
     * @return
     */
    boolean isExists(String url);

    /**
     * 批量更新状态
     * @param domains
     * @return
     */
    boolean batchUpdateUrlStatus(List<ImageDomain> domains);
}
