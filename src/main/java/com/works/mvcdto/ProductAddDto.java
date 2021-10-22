package com.works.mvcdto;

import com.works.entities.Product;
import com.works.entities.ProductCategories;
import com.works.entities.ProductCategory;
import com.works.repositories.ProductCategoryAddRepository;
import com.works.repositories.ProductCategoryRepository;
import com.works.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAddDto {
    final ProductCategoryAddRepository pcaRepo;
    final ProductCategoryRepository pCato;
    final ProductRepository pRepo;
    final ProductCategoryRepository pcato ;

    public ProductAddDto(ProductCategoryAddRepository pcaRepo, ProductCategoryRepository pCato, ProductRepository pRepo, ProductCategoryRepository pcato) {
        this.pcaRepo = pcaRepo;
        this.pCato = pCato;
        this.pRepo = pRepo;
        this.pcato = pcato;
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

        System.out.println("proCategpory : " + proCategory.getPcsid());
        pcaRepo.save(proCategory);
        System.out.println("Kayıt 3 Tamamlandı");
    }
    public ProductCategory addProductCategory(ProductCategory pcategory){
        ProductCategory productCategory = pCato.save(pcategory);
        return productCategory;
    }

    public Product updateProduct(Product product){
        Product lastProduct = pRepo.saveAndFlush(product);
        return lastProduct;
    }

/*    public List<ProductCategory> listProdCats( Integer[] categoryArray){

        return ls;
    }*/

}
