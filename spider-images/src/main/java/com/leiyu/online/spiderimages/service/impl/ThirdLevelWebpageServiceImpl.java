package com.leiyu.online.spiderimages.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.leiyu.online.spider.common.domain.ImageDomain;
import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.mapper.ImagesMapper;
import com.leiyu.online.spiderimages.service.AbstractWebpageService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class ThirdLevelWebpageServiceImpl extends AbstractWebpageService {

    @Autowired
    private ImagesMapper imagesMapper;
    @Override
    protected void handlePageInfos(Document document, UrlDomain urlDomain) {
        Elements elements = document.select(".news > img");
        if(null != elements && !elements.isEmpty()){
            for(int i = 0 ; i < elements.size();){
                String src = elements.get(i).attr("src");
                log.info("获取图片路径：{}",src);
                ImageDomain imageDomain = new ImageDomain();
                imageDomain.setNo(++i);
                imageDomain.setResourceName(getImageName(src));
                imageDomain.setResourceType(getResourceType(src));
                imageDomain.setSourceId(urlDomain.getId());
                imageDomain.setStatus(1);
                imageDomain.setUpdateTime(new Date());
                imageDomain.setUrl(src);
                imagesMapper.insertSelective(imageDomain);
                log.info("获得图片对象：{}",imageDomain);
            }
        }
    }

    private String getResourceType(String src) {
        if(StringUtils.isEmpty(src)){
            return src;
        }else {
            return src.substring(src.lastIndexOf("."));
        }
    }

    private String getImageName(String src) {
        if(StringUtils.isEmpty(src)){
            return src;
        }else {
            return src.substring(src.lastIndexOf("/") + 1);
        }
    }
}
