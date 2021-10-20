package com.works.repositories.redis;

import com.works.entities.redis.AdvertisementRedis;
import com.works.entities.redis.SurveyRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface SurveyRedisRepository extends CrudRepository<SurveyRedis, String> {

    Page<SurveyRedis> findByOrderBySidDesc(Pageable pageable);
}
