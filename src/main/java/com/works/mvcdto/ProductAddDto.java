package com.works.mvcdto;

import com.works.entities.ProductCategories;
import com.works.repositories.ProductCategoryAddRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductAddDto {
    final ProductCategoryAddRepository pcaRepo;


    public ProductAddDto(ProductCategoryAddRepository pcaRepo) {
        this.pcaRepo = pcaRepo;
    }
    public ProductCategories pCategoryfindbid(Integer category){
        ProductCategories productCategories = pcaRepo.findById(category).get();
        System.out.println("Kayıt 1 Tamamlandı");
        return productCategories;
    }
    public ProductCategories productSave(ProductCategories productCategories){
        ProductCategories prodCat = pcaRepo.save(productCategories);
        System.out.println("Kayıt 2 Tamamlandı");
        return prodCat;
    }
    public void productCategoryUpdate(ProductCategories proCategory){

        pcaRepo.saveAndFlush(proCategory);
        System.out.println("Kayıt 3 Tamamlandı");
    }

}
