package com.works.restcontrollers;

import com.works.dto.CustomerDto;
import com.works.entities.Customer;
import com.works.repositories.redis.CustomerRedisRepository;
import com.works.utils.ERest;
import com.works.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
@Api(value ="CustomerRestController",authorizations ={@Authorization(value = "basicAuth")})
public class CustomerRestController {

    final CustomerDto cDto;
    final CustomerRedisRepository crRepo;
    final Util util;

    public CustomerRestController(CustomerDto cDto, CustomerRedisRepository crRepo, Util util) {
        this.cDto = cDto;
        this.crRepo = crRepo;
        this.util = util;
    }
    @PostMapping("/add")
    public Map<ERest, Object> add(@RequestBody @Valid Customer customer, BindingResult bindingResult){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        if(bindingResult.hasErrors()){
            hm.put(ERest.status, false);
            hm.put(ERest.errors, util.errors(bindingResult) );
            return hm;
        }else{
            return  cDto.CustomerAdd(customer);
        }
    }
}
