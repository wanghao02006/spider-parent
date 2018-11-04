package com.leiyu.online.spiderimages.mapper;

import com.github.abel533.mapper.Mapper;
import com.leiyu.online.spider.common.domain.ImageDomain;
import com.leiyu.online.spider.common.domain.UrlDomain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ImagesMapper extends Mapper<ImageDomain> {

    @Update({
            "<script>",
            "update spider_images set status = 2 where id in",
            "<foreach item='item' index='index' collection='imageDomains' open='(' separator=',' close=')'>",
            "#{item.id}",
            "</foreach>",
            "</script>"
    })
    int batchUpdateStatus(@Param("imageDomains") List<ImageDomain> imageDomains);

    @Select("select * from spider_images where status=#{status} order by source_id asc")
    List<ImageDomain> selectTasks(ImageDomain query);
}
