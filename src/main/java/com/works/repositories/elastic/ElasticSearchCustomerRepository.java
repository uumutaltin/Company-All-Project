package com.works.repositories.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticCustomer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchCustomerRepository extends ElasticsearchRepository<ElasticCustomer, String> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"cname\":\"?0\"}}]}},\"from\":0,\"size\":10,\"sort\":[],\"aggs\":{}")
    Page<ElasticCustomer> findBySearch(String search , Pageable pageable);



    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"cname\":\"?0\"}}]}},\"from\":0,\"size\":10,\"sort\":[],\"aggs\":{}")
    List<ElasticCustomer> find(String search);
}
