package com.works.repositories;

import com.works.entities.Contents;
import com.works.entities.redis.ContentsRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents,Integer> {

    List<Contents> findByCstatusIsTrueOrderByCidDesc();

    List<Contents> findByCstatusIsFalseOrderByCidDesc();

    List<Contents> findByCstatusIsTrue(Pageable pageable);

    List<Contents> findByCstatusIsFalse(Pageable pageable);















}
