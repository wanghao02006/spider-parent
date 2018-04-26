package com.leiyu.online.spiderimages.service.impl;

import com.leiyu.online.spider.common.domain.UrlDomain;
import com.leiyu.online.spiderimages.mapper.UrlMapper;
import com.leiyu.online.spiderimages.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlServiceImpl implements UrlService {

    private static Logger logger = LoggerFactory.getLogger(UrlServiceImpl.class);

    @Autowired
    private UrlMapper urlMapper;

    @Override
    public List<UrlDomain> selectRootUrls() {
        UrlDomain urlDomain = new UrlDomain();
        urlDomain.setLevel(1);
        urlDomain.setIsParent(true);
        return urlMapper.select(urlDomain);
    }

    @Override
    public boolean insertOneUrl() {
        return false;
    }

    @Override
    public List<UrlDomain> selectUrlsByConditions(UrlDomain urlDomain) {
        return null;
    }
}
