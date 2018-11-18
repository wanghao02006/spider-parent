package com.leiyu.online.spiderimages.service;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spider.common.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Slf4j
public abstract class AbstractWebpageService implements WebpageService {

    private static final int RETRYCOUNTS = 3;

    @Override
    public void analysisWebPage(UrlDomain urlDomain) {
        Document document = getDocument(urlDomain.getPageUrl(),0);
        if(null == document){
            throw new RuntimeException("获取信息异常！");
        }
        handlePageInfos(document,urlDomain);
    }

    protected abstract void handlePageInfos(Document document,UrlDomain urlDomain);

    private Document getDocument(String url,int count){

        try {
            return JsoupUtil.getDocumentByJsoupBasic(url);
        } catch (IOException e) {
            log.info("第{}次获取网址信息失败，是否继续重试:{}",count,count < RETRYCOUNTS);
            if(count < RETRYCOUNTS){
                return getDocument(url,++count);
            }else {
                log.error("重试获取{}次失败！",RETRYCOUNTS);
                return null;
            }
        }
    }
}
