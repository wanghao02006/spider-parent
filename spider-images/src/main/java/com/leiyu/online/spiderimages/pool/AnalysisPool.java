package com.leiyu.online.spiderimages.pool;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spider.common.utils.JsoupUtil;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

@Component
public class AnalysisPool {

    @Resource(name = "findUrlService")
    private ExecutorService analysisService;

    public void submit(UrlDomain urlDomain){
        try {
            Document document = JsoupUtil.getDocumentByJsoupBasic(urlDomain.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        analysisService.submit(() -> {

        });
    }
}
