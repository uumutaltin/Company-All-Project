package com.works.controllers;

import com.works.documents.ElasticContents;
import com.works.documents.ElasticCustomer;
import com.works.entities.Customer;
import com.works.entities.redis.CustomerRedis;
import com.works.repositories.CustomerRepository;
import com.works.repositories.elastic.ElasticSearchCustomerRepository;
import com.works.repositories.redis.CustomerRedisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    Integer searchSize;
    final CustomerRepository cRepo;
    final CustomerRedisRepository crRepo;
    final ElasticSearchCustomerRepository ecRepo;

    public CustomerController(CustomerRepository cRepo, CustomerRedisRepository crRepo, ElasticSearchCustomerRepository ecRepo) {
        this.cRepo = cRepo;
        this.crRepo = crRepo;
        this.ecRepo = ecRepo;
    }


    @GetMapping("")
    public String customer(){
        return "customerManagement";
    }


    @ResponseBody
    @GetMapping("/customerList/{pageNo}/{stpageSize}")
    public List<CustomerRedis> customerList(@PathVariable String pageNo, @PathVariable String stpageSize ){
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        if (pageSize == -1){
            List<CustomerRedis> lsx = new ArrayList<>();
            Iterable<CustomerRedis> page = crRepo.findAll();
            List<Customer> crList = cRepo.findAll();
            ecRepo.deleteAll();
            for(CustomerRedis item : page){
                lsx.add(item);
            }
            crList.forEach(item ->{
                ElasticCustomer elasticCustomer = new ElasticCustomer();
                elasticCustomer.setCid(item.getCid());
                elasticCustomer.setCname(item.getCname());
                elasticCustomer.setCsurname(item.getCsurname());
                elasticCustomer.setEmail(item.getEmail());
                elasticCustomer.setMobile_phone(item.getMobile_phone());
                elasticCustomer.setBan(item.getBan());

                ecRepo.save(elasticCustomer);

            });
            Collections.reverse(lsx);
            return lsx;

        }else {
            Pageable pageable = PageRequest.of(ipageNumber,pageSize);
            Slice<CustomerRedis> pageList = crRepo.findByOrderByCidDesc(pageable);
            List<CustomerRedis> ls = pageList.getContent();
            List<Customer> customerList = cRepo.findAll();
            ecRepo.deleteAll();
            for(Customer item : customerList ) {
                ElasticCustomer elasticCustomer = new ElasticCustomer();
                elasticCustomer.setCid(item.getCid());
                elasticCustomer.setCname(item.getCname());
                elasticCustomer.setCsurname(item.getCsurname());
                elasticCustomer.setEmail(item.getEmail());
                elasticCustomer.setMobile_phone(item.getMobile_phone());
                elasticCustomer.setBan(item.getBan());
                ecRepo.save(elasticCustomer);
            }
            return  ls;
        }


    }


    @ResponseBody
    @GetMapping("/delete/{strcid}")
    public String customerDelete(@PathVariable String strcid){
        try {
            CustomerRedis customerRedis = crRepo.findById(strcid).get();
            int id = customerRedis.getCid();
            crRepo.delete(customerRedis);

            cRepo.deleteById(id);
        }catch (Exception e){
            System.err.println("Contents Delete Error : " + e);
        }
        return "customer";
    }

    @ResponseBody
    @GetMapping("/ban/{stBan}/{strcid}")
    public String customerBan(@PathVariable String strcid, @PathVariable Boolean stBan){
        try{
            CustomerRedis customerRedis = crRepo.findById(strcid).get();
            int id = customerRedis.getCid();
            customerRedis.setBan(stBan);
            Customer customer = cRepo.findById(id).get();
            customer.setBan(stBan);
            crRepo.save(customerRedis);
            cRepo.save(customer);

        }catch (Exception ex){
            System.err.println("Customer Ban Error:"+ ex);
        }
        return "customer";
    }

    @ResponseBody
    @GetMapping("/search/{pageNo}/{stpageSize}/{data}")
    public List<ElasticCustomer> contentSearch(@PathVariable String data, @PathVariable int pageNo, @PathVariable int stpageSize ){

        Page<ElasticCustomer> pages = ecRepo.findBySearch(data, PageRequest.of(pageNo,stpageSize));
        List<ElasticCustomer> list = pages.getContent();
        List<ElasticCustomer> listc = ecRepo.find(data);
        searchSize = listc.size();
        return  list;

    }

    @ResponseBody
    @GetMapping("/customerList/pageCount/{stpageSize}/{stPageStatus}")
    public Integer pageCount(@PathVariable String stpageSize,@PathVariable String stPageStatus){
        Integer pageStatus = Integer.parseInt(stPageStatus);
        long dataCount;
        if(pageStatus == 1) {
            dataCount = crRepo.count();
        }
        else {
            dataCount = searchSize;
        }
        double totalPageCount = Math.ceil((double)dataCount/Double.parseDouble(stpageSize));
        int pageCount = (int) totalPageCount;
        System.out.println("PageCount : " + pageCount);
        return pageCount;

    }


}
