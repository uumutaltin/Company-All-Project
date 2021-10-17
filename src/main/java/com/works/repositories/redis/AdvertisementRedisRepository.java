package com.works.repositories.redis;


import com.works.entities.redis.AdvertisementRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface AdvertisementRedisRepository extends CrudRepository<AdvertisementRedis,String> {

    Page<AdvertisementRedis> findByOrderByAidDesc(Pageable pageable);

}


