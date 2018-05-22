package com.leiyu.online.spiderimages.worker;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.service.UrlService;
import com.leiyu.online.spiderimages.service.WebpageService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class ParentPageWorker implements Job{

    private static Logger logger = LoggerFactory.getLogger(ParentPageWorker.class);

    @Autowired
    private UrlService urlService;

    @Resource(name = "rootWebpageServiceImpl")
    private WebpageService webpageService;

    @Resource(name = "findUrlService")
    private ExecutorService executorService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("服务器启动，开始检查是否有资源更新.......");
        List<UrlDomain> urls = urlService.selectRootUrls();

        for(final UrlDomain urlDomain : urls){
            executorService.submit(() -> {
                webpageService.analysisWebPage(urlDomain);
            });
        }
    }
}
