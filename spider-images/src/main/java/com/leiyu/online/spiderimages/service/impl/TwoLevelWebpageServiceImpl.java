package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.service.AbstractWebpageService;
import com.leiyu.online.spiderimages.service.UrlService;
import com.leiyu.online.spiderimages.service.WebpageService;
import com.leiyu.online.spiderimages.util.DirUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class TwoLevelWebpageServiceImpl extends AbstractWebpageService {

    @Resource(name = "findUrlService")
    private ExecutorService executorService;

    @Autowired
    private UrlService urlService;

    @Resource(name = "thirdLevelWebpageServiceImpl")
    private WebpageService webpageService;

    @Override
    @Transactional(readOnly = false)
    protected void handlePageInfos(Document document, UrlDomain urlDomain) {
        Elements elements = document.select(".news_list li");
        log.info("url:{},有{}网页",urlDomain,elements.size());
        if(null != elements && !elements.isEmpty()){
            int num = 0;
            for (Element element : elements){
                Element a = element.selectFirst("a");
                String name = a.attr("title");
                String url = a.attr("href");
                final UrlDomain result = savePages(name,url,urlDomain,num++);
//                executorService.submit(() -> {
//                    if(null != result){
//                        webpageService.analysisWebPage(result);
//                    }else {
//                        log.info("录入url：{}失败,resourceid:{}",url,urlDomain.getId());
//                    }
//
//                });

            }
        }
    }

    private UrlDomain savePages(String name, String url, UrlDomain urlDomain, int dirnum) {

        String dir = urlDomain.getDir() + File.separator + dirnum;

        DirUtils.mkdir(dir);

        url = getAllUrl(url,urlDomain.getBaseUrl());


        if(urlService.isExists(url)){
            log.info("url:{},已经采集，不需要再次录入！",url);
            return null;
        }else {
            UrlDomain saveDomain = new UrlDomain();
            saveDomain.setUrl(url);
            saveDomain.setParentId(urlDomain.getId());
            saveDomain.setBaseUrl(urlDomain.getBaseUrl());
            saveDomain.setIsParent(true);
            saveDomain.setLevel(urlDomain.getLevel() + 1);
            saveDomain.setUrlName(name);
            saveDomain.setStatus(1);
            saveDomain.setHasDownload(false);
            saveDomain.setUpdateTime(new Date());
            saveDomain.setResourceType(urlDomain.getResourceType());
            saveDomain.setDir(dir);
            urlService.insertOneUrl(saveDomain);
            return saveDomain;
        }
    }

    private String getAllUrl(String newUrl, String baseUrl) {
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(newUrl);
        boolean result = m.find();
        String find_result = null;
        if (result)
        {
            find_result = m.group(1);
        }
        return baseUrl + find_result + ".html";
    }
}
