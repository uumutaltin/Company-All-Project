package com.works.repositories.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticContents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchContentsRepository extends ElasticsearchRepository<ElasticContents,String> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy/MM/dd HH:mm:ss")
    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"ctitle\":\"?0\"}}]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    Page<ElasticContents> findBySearch(String search , Pageable pageable);



    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"ctitle\":\"?0\"}}]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    List<ElasticContents> find(String search);
}
