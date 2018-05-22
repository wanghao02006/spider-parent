package com.leiyu.online.spiderimages.service;

import com.leiyu.online.spider.common.domain.ImageDomain;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DownLoadImageServiceTest {

    @Autowired
    ImageService imageService;

    @Autowired
    DownLoadImageService downLoadImageService;

    @Resource(name = "downLoadImagesService")
    ExecutorService executorService;

    @Test
    public void testDownLoadImages(){
        ImageDomain query = new ImageDomain();
        query.setSourceId(53751l);
        List<ImageDomain> list = imageService.selectImagesByCodition(query);
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
        long start = System.currentTimeMillis();
        log.info("共需要下载文件个数：{}",list.size());
        for(ImageDomain domain : list){
            executorService.submit(() -> {
                downLoadImageService.downLoadImage(domain);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
            log.info("文件下载完毕，用时：{}",(System.currentTimeMillis() - start));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
