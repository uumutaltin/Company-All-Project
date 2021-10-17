package com.works.repositories.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticAdvertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

public interface ElasticSearchAdRepository extends ElasticsearchRepository<ElasticAdvertisement,String> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy/MM/dd HH:mm:ss")
    @Query("{\"bool\":{\"must\":[{\"match\":{\"atitle\":\"?0\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    Page<ElasticAdvertisement> findBySearch(String search, Pageable pageable);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"atitle\":\"?0\"}}],\"must_not\":[],\"should\":[]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    List<ElasticAdvertisement> find(String search);


}