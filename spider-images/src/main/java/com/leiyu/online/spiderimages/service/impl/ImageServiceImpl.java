package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.ImageDomain;
import com.leiyu.online.spiderimages.mapper.ImagesMapper;
import com.leiyu.online.spiderimages.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImagesMapper imagesMapper;

    @Override
    @Transactional(readOnly = false)
    public boolean insertOne(ImageDomain imageDomain) {
        try {
            return imagesMapper.insertSelective(imageDomain) == 1;
        }catch (Exception e){
            log.error("save image error,url is :",imageDomain.getUrl(),e);
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateOne(ImageDomain imageDomain) {
        return imagesMapper.updateByPrimaryKeySelective(imageDomain) <= 1;
    }

    @Override
    public List<ImageDomain> selectImagesByCodition(ImageDomain imageDomain) {
        return imagesMapper.selectTasks(imageDomain);
    }

    @Override
    public boolean isExists(String url) {
        ImageDomain query = new ImageDomain();
        query.setUrl(url);
        ImageDomain imageDomain = imagesMapper.selectOne(query);
        if(null != imageDomain){
            log.info("url:{},query domain:{}",url,imageDomain);
        }
        return null != imageDomain;
    }

    @Override
    public boolean batchUpdateUrlStatus(List<ImageDomain> domains) {
        log.info("batch update urls : {}", domains.size());
        return domains.size() == imagesMapper.batchUpdateStatus(domains);
    }
}
