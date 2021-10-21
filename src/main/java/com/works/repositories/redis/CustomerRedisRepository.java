package com.works.repositories.redis;

import com.works.entities.redis.ContentsRedis;
import com.works.entities.redis.CustomerRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface CustomerRedisRepository extends CrudRepository<CustomerRedis, String> {

    Page<CustomerRedis> findByOrderByCidDesc(Pageable pageable);
}
