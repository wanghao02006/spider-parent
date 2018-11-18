package com.leiyu.online.spiderimages.worker;

import com.github.pagehelper.PageHelper;
import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.service.UrlService;
import com.leiyu.online.spiderimages.service.WebpageService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ImagePageWorker implements Job{

    @Autowired
    UrlService urlService;

    @Resource(name="findUrlService")
    ExecutorService findUrlService;

    @Resource(name = "thirdLevelWebpageServiceImpl")
    private WebpageService webpageService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            downLoadImages();
        }catch (Exception e){
            log.error("解析地址异常！",e);
        }

    }

    private void downLoadImages() throws InterruptedException {
        UrlDomain query = new UrlDomain();
        query.setIsParent(true);
        query.setHasDownload(false);
        query.setLevel(3);
        PageHelper.startPage(1,1000);
        List<UrlDomain> lists = urlService.selectUrlsByConditions(query);
        log.info("list size:{}",lists.size());
        if(null != lists && lists.size() > 0){
            final CountDownLatch countDownLatch = new CountDownLatch(lists.size());
            for (final UrlDomain urldomain : lists) {
                findUrlService.submit(() -> {
                    try {
                        webpageService.analysisWebPage(urldomain);
                    }catch (Exception e){
                        log.error("error:",e);
                    }finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            log.info("执行完毕，执行下一批病更新当前数据");
            urlService.batchUpdateUrlStatus(lists);
            downLoadImages();
        }else {
            TimeUnit.MINUTES.sleep(1);
        }
    }
}
