package com.works.repositories;

import com.works.entities.ProductCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface ProductCategoryAddRepository extends JpaRepository<ProductCategories,Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery =true , value = "DELETE FROM product_categories_pcategory where pcategory_pcid = :id")
    Integer deleteCategory(Integer id);

}
