package com.leiyu.online.spiderimages.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.leiyu.online.spider.common.domain.ImageDomain;
import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.enums.ElementRules;
import com.leiyu.online.spiderimages.service.AbstractWebpageService;
import com.leiyu.online.spiderimages.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class ThirdLevelWebpageServiceImpl extends AbstractWebpageService {

    @Autowired
    private ImageService imageService;
    @Override
    protected void handlePageInfos(Document document, UrlDomain urlDomain) {
        Elements elements = document.select(ElementRules.getRule(urlDomain.getHandleType()).getImgSelector());
        if(null != elements && !elements.isEmpty()){
            int num = 0;
            for(Element element : elements){
                String src = element.attr(ElementRules.getRule(urlDomain.getHandleType()).getImgPath());
                if(imageService.isExists(src)){
                    log.info("获取图片路径：{}已采集,resourceid:{}",src,urlDomain.getId());
                }else{
                    ImageDomain imageDomain = new ImageDomain();
                    imageDomain.setNo(++num);
                    imageDomain.setResourceName(getImageName(src));
                    imageDomain.setResourceType(getResourceType(src));
                    imageDomain.setSourceId(urlDomain.getId());
                    imageDomain.setStatus(1);
                    imageDomain.setUpdateTime(new Date());
                    imageDomain.setUrl(null == src ? "" : src.trim());
                    imageDomain.setDir(urlDomain.getDir());
                    imageService.insertOne(imageDomain);
                }
            }
        }else {
            log.info("not found images:{}",urlDomain.getUrl());
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
