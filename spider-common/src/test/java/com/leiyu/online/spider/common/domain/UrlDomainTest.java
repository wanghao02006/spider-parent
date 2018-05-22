package com.leiyu.online.spider.common.domain;

import org.junit.Test;

public class UrlDomainTest {

    @Test
    public void testUrlDomain(){
        UrlDomain urlDomain = new UrlDomain();
        urlDomain.setId(111l);
        System.out.println(urlDomain);
    }
}
