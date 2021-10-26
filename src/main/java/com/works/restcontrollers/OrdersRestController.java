package com.works.restcontrollers;

import com.works.dto.OrdersDto;
import com.works.repositories.OrdersRepository;
import com.works.utils.ERest;
import com.works.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrdersRestController {

    final Util util;
    final OrdersDto oDto;


    public OrdersRestController(Util util, OrdersRepository oRepo, OrdersDto oDto) {
        this.util = util;
        this.oDto = oDto;
    }

    @GetMapping("/siparisVer/{productId}/{cid}")
    public Map<ERest, Object> add(@PathVariable Integer productId ,@PathVariable Integer cid, @RequestParam("adress") String adress){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        return oDto.ordersAdd(productId,cid,adress);

    }

    @GetMapping("/siparisListesi/{cid}")
    public Map<ERest, Object> userActiveList(@PathVariable Integer cid ){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        return oDto.userActiveOrders(cid);

    }

    @GetMapping("/gecmisSiparisListesi/{cid}")
    public Map<ERest, Object> userPassiveList(@PathVariable Integer cid ){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        return oDto.userPassiveOrders(cid);

    }
}
