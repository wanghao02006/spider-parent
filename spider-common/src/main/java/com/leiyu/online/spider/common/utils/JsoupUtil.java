package com.leiyu.online.spider.common.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class JsoupUtil {
    public static Document getDocumentByJsoupBasic(String url) throws IOException {

        // 设置连接超时和读数超时
        // 设置忽略过期页面
        return Jsoup.connect(url).timeout(120000).ignoreHttpErrors(true)
                .ignoreContentType(true).get();
    }

}
