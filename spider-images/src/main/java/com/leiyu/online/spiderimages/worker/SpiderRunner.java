package com.leiyu.online.spiderimages.worker;

import com.leiyu.online.spiderimages.quartz.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SpiderRunner implements ApplicationRunner {

    @Autowired
    QuartzManager quartzManager;

    public void testAddTask(){


    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        quartzManager.addJob("test","test","test","test",ParentPageWorker.class,"0 13 23 * * ?");
//        quartzManager.addJob("test","test","test","test",
//                ImagePageWorker.class,"30 42 22 * * ?");
        quartzManager.addJob("test","test","test","test",
                ImagePageWorker.class,"30 */30 * * * ?");
        quartzManager.addJob("test1","test1","test1","test1",
                DownloadImageWorker.class,"30 */5 * * * ?");
    }
}
