package com.works.controllers;

import com.works.entities.ProductCategories;
import com.works.entities.ProductCategory;
import com.works.mvcdto.ProductAddDto;
import com.works.repositories.ProductCategoryAddRepository;
import com.works.repositories.ProductCategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    final ProductCategoryAddRepository pcaRepo;
    final ProductAddDto pDto ;
    final ProductCategoryRepository pcato;

    public ProductController(ProductCategoryAddRepository pcaRepo, ProductAddDto pDto, ProductCategoryRepository pcato) {
        this.pcaRepo = pcaRepo;
        this.pDto = pDto;
        this.pcato = pcato;
    }

    @GetMapping("")
    public String product(){
        return "productManagement";
    }
    @GetMapping("/category/add")
    public String addProductCategory(Model model){
        model.addAttribute("productCategory", new ProductCategory());
        return "productCategoryAdd";
    }
    @GetMapping("/add")
    public String addProduct(){
        return "productAdd";
    }

    @PostMapping("/category/add")
    public String productCategoryAdd(@Valid @ModelAttribute("productCategory") ProductCategory productCategory, BindingResult bindingResult, @RequestParam(value = "pcategory",defaultValue = "-1") Integer category){
        try{
            if (!bindingResult.hasErrors()) {

                if (category == -1){
                    ProductCategories  prodCategory = new ProductCategories();
                    prodCategory.setPcategoriesname(productCategory.getPcategoryname());
                    pcaRepo.save(prodCategory);
                    pcato.save(productCategory);

                }
                else{
                    ProductCategory beforeProdCat = pcato.save(productCategory);
                    ProductCategories proCategory = pDto.pCategoryfindbid(category);
                    List<ProductCategory> ls = proCategory.getPcategory();
                    ls.add(beforeProdCat);
                    proCategory.setPcategory(ls);
                    pDto.productCategoryUpdate(proCategory);
                }
                return "redirect:/products";
            }
        }catch (Exception e){
            System.err.println("ProductCategoryAdd Error : " + e);
        }
        return "productCategoryAdd";

    }

    @ResponseBody
    @GetMapping("/category/delete/{stCid}")
    public Boolean productCategoryDelete(@PathVariable String stCid){
        try{
            Integer cid = Integer.parseInt(stCid);
            pcaRepo.deleteById(cid);
            return true ;
        }catch (Exception e){
            System.err.println("ProductCategoryDelete : " + e);
            return false;
        }

    }

    @ResponseBody
    @GetMapping("/category/list")
    public List<ProductCategories> productCategoryList(){
    List<ProductCategories> productCategories = pcaRepo.findAll();
    return productCategories;
    }
}

