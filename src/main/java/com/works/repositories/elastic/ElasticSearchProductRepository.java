package com.works.repositories.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticNews;
import com.works.documents.ElasticProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchProductRepository extends ElasticsearchRepository<ElasticProduct,String> {


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy/MM/dd HH:mm:ss")
    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"pname\":\"?0\"}},{\"match\":{\"psdetail\":\"?0\"}},{\"match\":{\"pcategories.pcategoryname\":\"?0\"}}]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    Page<ElasticProduct> findBySearch(String search, Pageable pageable);

    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"pname\":\"?0\"}},{\"match\":{\"psdetail\":\"?0\"}},{\"match\":{\"pcategories.pcategoryname\":\"?0\"}}]}},\"from\":0,\"size\":?0,\"sort\":[],\"aggs\":{}")
    List<ElasticProduct> find(String search);
}
