package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.PageDomain;
import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.enums.ElementRules;
import com.leiyu.online.spiderimages.service.AbstractWebpageService;
import com.leiyu.online.spiderimages.service.WebpageService;
import com.leiyu.online.spiderimages.util.DirUtils;
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



    @Resource(name = "findUrlService")
    private ExecutorService executorService;

    @Resource(name = "twoLevelWebpageServiceImpl")
    private WebpageService webpageService;

    @Override
    protected void handlePageInfos(Document document,UrlDomain urlDomain) {

        final String rootUrl = urlDomain.getHandleType() + File.separator
                + urlDomain.getDir();

        DirUtils.mkdir(rootUrl);

        Elements elements = document.select(ElementRules.getRule(urlDomain.getHandleType()).getPageUrl());
        if(null != elements && !elements.isEmpty()){
            Element element = elements.last();
            int totalpages = getTotalPages(element.attr("href"));
            for (int i = 1 ; i <= totalpages ; i++){
                final UrlDomain level2 = new UrlDomain();
                level2.setUrl(getNewUrl(urlDomain.getUrl(), i));
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
                level2.setDir(rootUrl + File.separator + "page-" + i);
                level2.setHandleType(urlDomain.getHandleType());
                executorService.submit(() ->{
                    webpageService.analysisWebPage(level2);
                });

            }
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

    private String getNewUrl(String url, int index){
        return url.replaceAll("(\\d+)", String.valueOf(index));
    }

    public static void main(String[] args) {
        Pattern p = Pattern.compile("(\\d+)");

        System.out.println("/tupianqu/siwa/index_2030.html".replaceAll("(\\d+)","2"));
        System.out.println("/photolist/allxz/sj/p5.html".replaceAll("(\\d+)","2"));
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
