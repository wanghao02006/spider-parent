package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.mapper.UrlMapper;
import com.leiyu.online.spiderimages.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    private static Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Autowired
    private UrlMapper urlMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UrlDomain> selectRootUrls() {
        UrlDomain urlDomain = new UrlDomain();
        urlDomain.setLevel(1);
        urlDomain.setStatus(1);
        urlDomain.setHasDownload(false);
        urlDomain.setIsParent(true);
        return urlMapper.select(urlDomain);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean insertOneUrl(UrlDomain urlDomain) {
        return 1 == urlMapper.insertSelective(urlDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UrlDomain> selectUrlsByConditions(UrlDomain urlDomain) {
        return urlMapper.select(urlDomain);
    }

    @Override
    public boolean isExists(String url) {
        UrlDomain urlDomain = new UrlDomain();
        urlDomain.setUrl(url);
        UrlDomain result = urlMapper.selectOne(urlDomain);
        return null != result;
    }

    public boolean batchUpdateUrlStatus(List<UrlDomain> domains){
        log.info("batch update urls : {}", domains.size());
        return domains.size() == urlMapper.batchUpdateStatus(domains);
    }
}
