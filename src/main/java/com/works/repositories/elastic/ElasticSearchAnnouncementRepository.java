package com.works.repositories.elastic;

import com.works.documents.ElasticAnnouncement;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchAnnouncementRepository extends ElasticsearchRepository<ElasticAnnouncement,String> {
}
