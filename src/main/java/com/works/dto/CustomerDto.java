package com.works.dto;

import com.works.entities.Customer;
import com.works.entities.redis.CustomerRedis;
import com.works.repositories.CustomerRepository;
import com.works.repositories.redis.CustomerRedisRepository;
import com.works.utils.ERest;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CustomerDto {
    final CustomerRepository cRepo;
    final CustomerRedisRepository crRepo;

    public CustomerDto(CustomerRepository cRepo, CustomerRedisRepository crRepo) {
        this.cRepo = cRepo;
        this.crRepo = crRepo;
    }

    public Map<ERest, Object> CustomerAdd(Customer customer){
        Map<ERest, Object> hm = new LinkedHashMap<>();

        try{
            Customer c = cRepo.save(customer);

            CustomerRedis cr = new CustomerRedis();
            hm.put(ERest.status, true);
            hm.put(ERest.message,"Customer Ekleme Başarılı");
            hm.put(ERest.result, c);


            cr.setCid(c.getCid());
            cr.setCname(c.getCname());
            cr.setCsurname(c.getCsurname());
            cr.setEmail(c.getEmail());
            cr.setMobile_phone(c.getMobile_phone());
            cr.setBan(c.getBan());

            crRepo.save(cr);

        }
        catch (Exception ex){
            hm.put(ERest.status,false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, " HATA! ->Bu E-mail (" + customer.getEmail()+") zaten kullanılıyor var");
            }

        }
        return hm;
    }
}
