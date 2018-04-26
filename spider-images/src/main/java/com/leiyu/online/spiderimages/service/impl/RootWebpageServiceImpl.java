package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.service.AbstractWebpageService;
import com.leiyu.online.spiderimages.service.WebpageService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 原始网址解析
 */
@Slf4j
@Service
public class RootWebpageServiceImpl extends AbstractWebpageService {

    private static final String BASEURL = "/media/images/";

    @Resource(name = "findUrlService")
    private ExecutorService executorService;

    @Resource(name = "twoLevelWebpageServiceImpl")
    private WebpageService webpageService;

    @Override
    protected void handlePageInfos(Document document,UrlDomain urlDomain) {

        File file = new File(BASEURL + File.separator + urlDomain.getResourceType() + File.separator
                + urlDomain.getUrlName());
        if(!file.exists()){
            file.mkdirs();
        }

        Elements elements = document.select(".page > a");
        if(null != elements && !elements.isEmpty()){
            Element element = elements.last();
            int totalpages = getTotalPages(element.attr("href"));
            for (int i = 1 ; i <= totalpages ; i++){
                log.info("获取地址：{}index_{}.html",urlDomain.getUrl(),i);
                UrlDomain level2 = new UrlDomain();
                if(i == 1){
                    level2.setUrl(urlDomain.getUrl());
                }else {
                    level2.setUrl(urlDomain.getBaseUrl() + "index_" + i + ".html");
                }
                level2.setUpdateTime(new Date());
                level2.setHasDownload(false);
                level2.setStatus(1);
                level2.setUrlName(urlDomain.getUrlName());
                level2.setBaseUrl(urlDomain.getBaseUrl());
                level2.setLevel(urlDomain.getLevel() + 1);
                level2.setParentId(urlDomain.getId());
                level2.setIsParent(true);
                level2.setId(urlDomain.getId());
                level2.setResourceType(urlDomain.getResourceType());
                if(i == 1){
                    executorService.submit(() ->{
                        webpageService.analysisWebPage(level2);
                    });
                }
                break;

            }
        }
        try {
            TimeUnit.MINUTES.sleep(10l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private int getTotalPages(String url){
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(url);
        boolean result = m.find();
        String find_result = null;
        if (result)
        {
            find_result = m.group(1);
        }
        return Integer.parseInt(find_result);
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher("/tupianqu/siwa/index_2030.html");
        boolean result = m.find();
        String find_result = null;
        if (result)
        {
            find_result = m.group(1);
        }
        System.out.println(find_result);
    }
}
