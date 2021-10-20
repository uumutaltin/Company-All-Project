package com.works.repositories;

import com.works.entities.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Integer> {
}
