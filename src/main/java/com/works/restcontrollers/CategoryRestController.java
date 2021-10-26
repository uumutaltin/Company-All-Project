package com.works.restcontrollers;

import com.works.dto.CategoryDto;
import com.works.utils.ERest;
import com.works.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/category")
@Api(value ="CategoryRestController",authorizations ={@Authorization(value = "basicAuth")})
public class CategoryRestController {

    final CategoryDto cDto;
    final Util util;

    public CategoryRestController(CategoryDto cDto, Util util) {
        this.cDto = cDto;
        this.util = util;
    }

    @GetMapping("/categoryList")
    public Map<ERest, Object> productCategoryList(){
        return cDto.productCategoryList();
    }
}
