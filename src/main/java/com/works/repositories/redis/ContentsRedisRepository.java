package com.works.repositories.redis;

import com.works.entities.Contents;
import com.works.entities.redis.ContentsRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableRedisRepositories
public interface ContentsRedisRepository extends CrudRepository<ContentsRedis,String> {

    Page<ContentsRedis> findByOrderByCidDesc(Pageable pageable);


    List<ContentsRedis> findByCstatusIsTrue();

}
