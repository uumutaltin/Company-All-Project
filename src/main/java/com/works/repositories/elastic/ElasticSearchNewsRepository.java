package com.works.repositories.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchNewsRepository extends ElasticsearchRepository<ElasticNews,String> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy/MM/dd HH:mm:ss")
    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"ntitle\":\"?0\"}},{\"match\":{\"newsCategory.nctitle\":\"?0\"}}]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    Page<ElasticNews> findBySearch(String search, Pageable pageable);

    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"ntitle\":\"?0\"}},{\"match\":{\"newsCategory.nctitle\":\"?0\"}}]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    List<ElasticNews> find(String search);

}
