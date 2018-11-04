package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.ImageDomain;
import com.leiyu.online.spiderimages.service.DownLoadImageService;
import com.leiyu.online.spiderimages.service.ImageService;
import com.leiyu.online.spiderimages.util.DirUtils;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
@Slf4j
public class DownLoadImageServiceImpl implements DownLoadImageService {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ImageService imageService;

    @Override
    public String downLoadImage(ImageDomain imageDomain) {
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(imageDomain.getUrl());

            HttpResponse response = httpClient.execute(httpGet);
            if(response.getStatusLine().getStatusCode() == 200){
                HttpEntity entity = response.getEntity();
                if(entity.isStreaming()){
                    long size = entity.getContentLength();
                    String baseDir = DirUtils.BASEURL + imageDomain.getDir() + File.separator ;
                    File dir = new File(baseDir);
                    if(!dir.exists()){
                        dir.mkdirs();
                    }
                    File image = new File(baseDir + imageDomain.getResourceName());
                    if(image.exists()){
                        image.delete();
                    }
                    @Cleanup OutputStream outputStream = new FileOutputStream(image);
                    entity.writeTo(outputStream);
                    outputStream.flush();

                    imageDomain.setSize(size);
                    imageDomain.setStatus(2);
                    imageService.updateOne(imageDomain);
                    return null;
                }else {
                    log.info("获取信息不是流信息，无法输出");
                }
            }else {
                log.info("下载失败，失败代码：{},url:{}",response.getStatusLine().getStatusCode(),imageDomain.getUrl());
            }
        } catch (ClientProtocolException e) {
            log.error("下载文件出现协议异常，url:{}，异常信息:",imageDomain.getUrl(),e);
        } catch (IOException e) {

        } catch (Exception e){
            log.error("下载文件其他异常，url:{}异常信息",imageDomain.getUrl(),e);
        }finally {
            if(httpGet != null){
                httpGet.releaseConnection();
            }
        }
        imageDomain.setSize(0l);
        imageDomain.setStatus(3);
        imageService.updateOne(imageDomain);
        return null;
    }
}
