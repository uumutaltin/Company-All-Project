package com.works.dto;

import com.works.documents.ElasticProduct;
import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import com.works.repositories.elastic.ElasticSearchProductRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductDto {
    final ProductRepository pRepo;
    final ElasticSearchProductRepository espRepo;
    public ProductDto(ProductRepository pRepo, ElasticSearchProductRepository espRepo) {
        this.pRepo = pRepo;
        this.espRepo = espRepo;
    }

    public Map<ERest,Object> productList(String pageNumber,String stPageSize){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        int ipageNumber = Integer.parseInt(pageNumber);
        int pageSize = Integer.parseInt(stPageSize);
        try {
            if (pageSize == -1) {
                List<Product> page = pRepo.findAll();
                hm.put(ERest.status,true);
                hm.put(ERest.message, "Ürün Listeleme işlemi başarılı");
                hm.put(ERest.result, page);
                for (Product item : page){
                    ElasticProduct elasticProduct = new ElasticProduct();
                    elasticProduct.setPid(item.getPid());
                    elasticProduct.setPname(item.getPname());
                    elasticProduct.setPsdetail(item.getPsdetail());
                    elasticProduct.setPdetail(item.getPdetail());
                    elasticProduct.setPprice(item.getPprice());
                    elasticProduct.setPtype(item.getPtype());
                    elasticProduct.setPcampaign(item.getPcampaign());
                    elasticProduct.setPcampaignname(item.getPcampaignname());
                    elasticProduct.setPcampaigndetail(item.getPcampaigndetail());
                    elasticProduct.setPadress(item.getPadress());
                    elasticProduct.setPlatitude(item.getPlatitude());
                    elasticProduct.setPlongitude(item.getPlongitude());
                    elasticProduct.setPimages(item.getPimages());
                    elasticProduct.setPcategories(item.getPcategories());

                    espRepo.save(elasticProduct);
                }
            }else{
                Pageable pageable = PageRequest.of(ipageNumber, pageSize);
                List<Product> productPageablels = pRepo.findByOrderByPidDesc(pageable);
                for (Product item : productPageablels){
                    ElasticProduct elasticProduct = new ElasticProduct();
                    elasticProduct.setPid(item.getPid());
                    elasticProduct.setPname(item.getPname());
                    elasticProduct.setPsdetail(item.getPsdetail());
                    elasticProduct.setPdetail(item.getPdetail());
                    elasticProduct.setPprice(item.getPprice());
                    elasticProduct.setPtype(item.getPtype());
                    elasticProduct.setPcampaign(item.getPcampaign());
                    elasticProduct.setPcampaignname(item.getPcampaignname());
                    elasticProduct.setPcampaigndetail(item.getPcampaigndetail());
                    elasticProduct.setPadress(item.getPadress());
                    elasticProduct.setPlatitude(item.getPlatitude());
                    elasticProduct.setPlongitude(item.getPlongitude());
                    elasticProduct.setPimages(item.getPimages());
                    elasticProduct.setPcategories(item.getPcategories());

                    espRepo.save(elasticProduct);
                }
                hm.put(ERest.status,true);
                hm.put(ERest.message, "Ürün Listeleme işlemi başarılı");
                hm.put(ERest.result, productPageablels);
            }
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Ürün Listeleme sırasında hata oluştu!");
        }
        return hm;
    }

    public Map<ERest,Object> list(){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {

                Pageable pageable = PageRequest.of(0, 10);
                List<Product> productPageablels = pRepo.findByOrderByPidDesc(pageable);
            for (Product item : productPageablels){
                ElasticProduct elasticProduct = new ElasticProduct();
                elasticProduct.setPid(item.getPid());
                elasticProduct.setPname(item.getPname());
                elasticProduct.setPsdetail(item.getPsdetail());
                elasticProduct.setPdetail(item.getPdetail());
                elasticProduct.setPprice(item.getPprice());
                elasticProduct.setPtype(item.getPtype());
                elasticProduct.setPcampaign(item.getPcampaign());
                elasticProduct.setPcampaignname(item.getPcampaignname());
                elasticProduct.setPcampaigndetail(item.getPcampaigndetail());
                elasticProduct.setPadress(item.getPadress());
                elasticProduct.setPlatitude(item.getPlatitude());
                elasticProduct.setPlongitude(item.getPlongitude());
                elasticProduct.setPimages(item.getPimages());
                elasticProduct.setPcategories(item.getPcategories());

                espRepo.save(elasticProduct);
            }
                hm.put(ERest.status,true);
                hm.put(ERest.message, "Ürün Listeleme işlemi başarılı");
                hm.put(ERest.result, productPageablels);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Ürün Listeleme sırasında hata oluştu!");
        }
        return hm;
    }

    public Map<ERest,Object> search(Integer pageNo, Integer stpageSize, String data){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        Page<ElasticProduct> pages = espRepo.findBySearch(data,PageRequest.of(pageNo,stpageSize));
        List<ElasticProduct> list = pages.getContent();

        hm.put(ERest.status,true);
        hm.put(ERest.message, "Ürün Arama işlemi başarılı");
        hm.put(ERest.result, list);
        return hm;
    }
    public Map<ERest,Object> findProduct(Integer prodId){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        Product product = pRepo.findById(prodId).get();

        hm.put(ERest.status,true);
        hm.put(ERest.message, "Ürün Arama işlemi başarılı");
        hm.put(ERest.result, product);
        return hm;
    }



}
