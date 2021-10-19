package com.works.repositories;

import com.works.entities.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents,Integer> {

    List<Contents> findByCstatusIsTrueOrderByCidDesc();











}
