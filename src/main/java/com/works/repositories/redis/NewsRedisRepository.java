package com.works.repositories.redis;

import com.works.entities.redis.AnnouncementRedis;
import com.works.entities.redis.NewsRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface NewsRedisRepository extends CrudRepository<NewsRedis,String> {

    Page<NewsRedis> findByOrderByNidDesc(Pageable pageable);

}
