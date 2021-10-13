package com.works.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    @GetMapping("")
    public String product(){
        return "productManagement";
    }
    @GetMapping("/category/add")
    public String addProductCategory(){
        return "productCategoryAdd";
    }
    @GetMapping("/add")
    public String addProduct(){
        return "productAdd";
    }
}
