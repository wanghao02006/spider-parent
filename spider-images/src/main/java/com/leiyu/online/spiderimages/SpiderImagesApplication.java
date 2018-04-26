package com.leiyu.online.spiderimages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpiderImagesApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(SpiderImagesApplication.class, args);
	}
}
