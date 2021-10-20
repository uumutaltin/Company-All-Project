package com.works.repositories.elastic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticAdvertisement;
import com.works.documents.ElasticSurvey;
import com.works.entities.Survey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchSurveyRepository extends ElasticsearchRepository<ElasticSurvey, String> {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"stitle\":\"?0\"}}]}},\"from\":0,\"size\":10,\"sort\":[],\"aggs\":{}")
    Page<ElasticSurvey> findBySearch(String search, Pageable pageable);

    @Query("{\"bool\":{\"must\":[],\"must_not\":[],\"should\":[{\"match\":{\"stitle\":\"?0\"}}]}},\"from\":0,\"size\":10,\"sort\":[],\"aggs\":{}")
    List<ElasticSurvey> find(String search);
}
