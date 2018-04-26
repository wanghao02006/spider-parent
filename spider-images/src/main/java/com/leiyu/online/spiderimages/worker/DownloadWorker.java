package com.leiyu.online.spiderimages.worker;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.service.UrlService;
import com.leiyu.online.spiderimages.service.WebpageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
public class DownloadWorker implements ApplicationRunner{

    private static Logger logger = LoggerFactory.getLogger(DownloadWorker.class);

    @Autowired
    private UrlService urlService;

    @Resource(name = "rootWebpageServiceImpl")
    private WebpageService webpageService;

    @Resource(name = "findUrlService")
    private ExecutorService executorService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("服务器启动，开始检查是否有资源更新.......");
        List<UrlDomain> urls = urlService.selectRootUrls();

        for(UrlDomain urlDomain : urls){
            executorService.submit(() -> {
                webpageService.analysisWebPage(urlDomain);
            });
        }
    }
}
