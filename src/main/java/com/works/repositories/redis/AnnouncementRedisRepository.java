package com.works.repositories.redis;

import com.works.entities.redis.AnnouncementRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface AnnouncementRedisRepository extends CrudRepository<AnnouncementRedis,String> {

    Page<AnnouncementRedis> findByOrderByAidDesc(Pageable pageable);


}
