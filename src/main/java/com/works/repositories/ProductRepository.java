package com.works.repositories;

import com.works.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByOrderByPidDesc(Pageable pageable);

    @Modifying
    @Transactional
    @Query(nativeQuery =true , value = "DELETE FROM product_pcategories where product_pid = :id")
    Integer deleteProducRelationalCategory(Integer id);

    @Modifying
    @Transactional
    @Query(nativeQuery =true , value = "DELETE FROM product_pimages where pimages_iid = :id")
    Integer deleteProductRelationaImage(Integer id);
}
