package com.works.restcontrollers;

import com.works.dto.ProductDto;
import com.works.utils.ERest;
import com.works.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@Api(value ="ProductRestController",authorizations ={@Authorization(value = "basicAuth")})
public class ProductRestController {
    final ProductDto pDto;
    final Util util;
    public ProductRestController(ProductDto pDto, Util util) {
        this.pDto = pDto;
        this.util = util;
    }

    @GetMapping("/list/{stPageNo}/{stPageSize}")
    public Map<ERest, Object> listCountSize(@PathVariable String stPageNo , @PathVariable String stPageSize){
        Map<ERest,Object> hm = new LinkedHashMap<>();

            return pDto.productList(stPageNo,stPageSize);
        }

    @GetMapping("/list")
    public Map<ERest, Object> list(){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        return pDto.list();
    }

    @GetMapping("/ListSearch/{sentence}/{stPageCount}/{stpageSize}")
    public Map<ERest, Object> search(@PathVariable String sentence , @PathVariable Integer stPageCount , @PathVariable Integer stpageSize){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        return pDto.search(stPageCount,stpageSize,sentence);
    }

    @GetMapping("/{prodId}")
    public Map<ERest, Object> findProduct( @PathVariable Integer prodId){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        return pDto.findProduct(prodId);
    }
}
