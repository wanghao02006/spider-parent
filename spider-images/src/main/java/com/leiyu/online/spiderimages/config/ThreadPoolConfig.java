package com.leiyu.online.spiderimages.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService findUrlService(){
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public ExecutorService downLoadImagesService(){
        return Executors.newFixedThreadPool(4);
    }
}
