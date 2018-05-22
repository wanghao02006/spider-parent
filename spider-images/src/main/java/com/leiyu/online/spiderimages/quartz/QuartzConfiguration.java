package com.leiyu.online.spiderimages.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    public SchedulerFactoryBean scheduler(JobFactory jobFactory){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setSchedulerName("leiyuscheduler");
        schedulerFactoryBean.setApplicationContext(applicationContext);
        return schedulerFactoryBean;
    }

    @Bean
    public JobFactory jobFactory(){
        return new JobFactory();
    }
}
