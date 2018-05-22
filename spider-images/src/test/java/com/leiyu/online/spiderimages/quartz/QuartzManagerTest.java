package com.leiyu.online.spiderimages.quartz;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuartzManagerTest {
    @Autowired
    QuartzManager quartzManager;

    @Test
    public void testAddTask(){
        quartzManager.addJob("test","test","test","test",MyJob.class,"0/5 * * * * ?");
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

@Component
class MyJob implements Job {

    @Autowired
    UrlService urlService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<UrlDomain> list = urlService.selectRootUrls();
        System.out.println("hello quartz " + list.size());
    }
}
