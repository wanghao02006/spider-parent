package com.leiyu.online.spiderimages.mapper;

import com.github.abel533.mapper.Mapper;
import com.leiyu.online.spider.common.domain.UrlDomain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UrlMapper extends Mapper<UrlDomain> {

    @Update({
            "<script>",
            "update spider_url set has_download = 1 where id in",
            "<foreach item='item' index='index' collection='urlDomains' open='(' separator=',' close=')'>",
            "#{item.id}",
            "</foreach>",
            "</script>"
    })
    int batchUpdateStatus(@Param("urlDomains") List<UrlDomain> urlDomains);
}
