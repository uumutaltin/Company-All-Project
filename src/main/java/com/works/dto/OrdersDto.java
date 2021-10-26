package com.works.dto;


import com.works.entities.restentity.Orders;
import com.works.entities.restentity.UserLike;
import com.works.repositories.OrdersRepository;
import com.works.utils.ERest;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersDto {
    final OrdersRepository oRepo;

    public OrdersDto(OrdersRepository oRepo) {
        this.oRepo = oRepo;
    }

    public Map<ERest,Object> ordersAdd(Integer productId , Integer customerId,String adress){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            Orders order = new Orders();
           order.setCustomerid(customerId);
           order.setProductid(productId);
            order.setAdress(adress);
            Orders o = oRepo.save(order);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Ekleme başarılı");
            hm.put(ERest.result, o );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            hm.put(ERest.message,"Ekleme sırasında hata oluştu!");
        }
        return hm;
    }

    public Map<ERest,Object> userActiveOrders(Integer customerId){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            List<Orders> o = oRepo.userActiveOrders(customerId);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Listeleme başarılı");
            hm.put(ERest.result, o );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            hm.put(ERest.message,"Listeleme sırasında hata oluştu!");
        }
        return hm;
    }

    public Map<ERest,Object> userPassiveOrders(Integer customerId){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            List<Orders> o = oRepo.userPassiveOrders(customerId);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Listeleme başarılı");
            hm.put(ERest.result, o );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            hm.put(ERest.message,"Listeleme sırasında hata oluştu!");
        }
        return hm;
    }
}
