package com.works.repositories;

import com.works.entities.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryAddRepository extends JpaRepository<ProductCategories,Integer> {
}
