package com.works.dto;

import com.works.entities.ProductCategories;
import com.works.repositories.ProductCategoryAddRepository;
import com.works.utils.ERest;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryDto {

    final ProductCategoryAddRepository pcaRepo;

    public CategoryDto(ProductCategoryAddRepository pcaRepo) {
        this.pcaRepo = pcaRepo;
    }


    public Map<ERest, Object> productCategoryList(){

        Map<ERest,Object> hm = new LinkedHashMap<>();

        try{
            List<ProductCategories> productCategories = pcaRepo.findAll();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Kategori Listeleme işlemi başarılı");
            hm.put(ERest.result, productCategories);

        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Kategori Listeleme işlemi sırasında hata oluştu!");
        }

        return hm;
    }
}
