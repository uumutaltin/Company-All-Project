package com.works.repositories;

import com.works.entities.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImages,Integer> {


}
