package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.mapper.UrlMapper;
import com.leiyu.online.spiderimages.service.AbstractWebpageService;
import com.leiyu.online.spiderimages.service.WebpageService;
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

    private static final String BASEURL = "/media/images/";

    @Resource(name = "findUrlService")
    private ExecutorService executorService;

    @Autowired
    private UrlMapper urlMapper;

    @Resource(name = "thirdLevelWebpageServiceImpl")
    private WebpageService webpageService;

    @Override
    @Transactional(readOnly = false)
    protected void handlePageInfos(Document document, UrlDomain urlDomain) {
        Elements elements = document.select(".news_list li");
        if(null != elements && !elements.isEmpty()){
            for (Element element : elements){
                Element a = element.selectFirst("a");
                String name = a.attr("title");
                String url = a.attr("href");
                executorService.submit(() -> {
                    webpageService.analysisWebPage(savePages(name,url,urlDomain));
                });

            }
        }
    }

    private UrlDomain savePages(String name, String url, UrlDomain urlDomain) {

        File file = new File(BASEURL + File.separator + urlDomain.getResourceType() + File.separator
                + urlDomain.getUrlName() + File.separator + name);
        if(!file.exists()){
            file.mkdirs();
        }

        url = getAllUrl(url,urlDomain.getBaseUrl());
        log.info("获取全路径为：{}",url);
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

        urlMapper.insertSelective(saveDomain);
        return saveDomain;
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
