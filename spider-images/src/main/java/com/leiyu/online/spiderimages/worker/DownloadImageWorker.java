package com.leiyu.online.spiderimages.worker;

import com.github.pagehelper.PageHelper;
import com.leiyu.online.spider.common.domain.ImageDomain;
import com.leiyu.online.spiderimages.service.DownLoadImageService;
import com.leiyu.online.spiderimages.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Slf4j
public class DownloadImageWorker implements Job {
    @Autowired
    ImageService imageService;

    @Autowired
    DownLoadImageService downLoadImageService;

    @Resource(name="downLoadImagesService")
    ExecutorService downLoadImagesService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        downLoadImages();
    }

    public void downLoadImages(){
        ImageDomain query = new ImageDomain();
        query.setStatus(4);
        PageHelper.startPage(1,100);
        List<ImageDomain> images = imageService.selectImagesByCodition(query);
        if(null != images && images.size() > 0){
            log.info("start download images,images size:{}",images.size());
            CountDownLatch countDownLatch = new CountDownLatch(images.size());
            for(final ImageDomain imageDomain : images){
                downLoadImagesService.submit(() -> {
                    try {
                        downLoadImageService.downLoadImage(imageDomain);
                    }catch (Exception e){
                        log.error("download images error,url:{}",imageDomain.getUrl(),e);
                    }finally {
                        countDownLatch.countDown();
                    }
                });
            }
            try {

                countDownLatch.await();
//                imageService.batchUpdateUrlStatus(images);
                downLoadImages();
                log.info("has downloaded one batch,start next batch");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
