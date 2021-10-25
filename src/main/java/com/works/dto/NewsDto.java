package com.works.dto;

import com.works.entities.News;
import com.works.entities.redis.ContentsRedis;
import com.works.entities.redis.NewsRedis;
import com.works.repositories.NewsRepository;
import com.works.repositories.redis.NewsRedisRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsDto {

    final NewsRedisRepository nrRepo;
    final NewsRepository nRepo;

    public NewsDto(NewsRedisRepository nrRepo, NewsRepository nRepo) {
        this.nrRepo = nrRepo;
        this.nRepo = nRepo;
    }

    public Map<ERest, Object> newsList(String pageNo, String stpageSize){

        Map<ERest,Object> hm = new LinkedHashMap<>();

        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        try {

            if(pageSize == -1){
                List<NewsRedis> lsx = new ArrayList<>();
                Iterable<NewsRedis> page = nrRepo.findAll();

                for (NewsRedis item : page) {
                    lsx.add(item);
                }
                Collections.reverse(lsx);
                hm.put(ERest.status,true);
                hm.put(ERest.message, "Haber Listeleme işlemi başarılı");
                hm.put(ERest.result, lsx);

            }else {
                Pageable pageable = PageRequest.of(ipageNumber, pageSize);
                List<NewsRedis> ls = new ArrayList<>();
                Iterable<NewsRedis> pageList = nrRepo.findByOrderByNidDesc(pageable);

                for (NewsRedis item : pageList){
                    ls.add(item);
                }

                hm.put(ERest.status,true);
                hm.put(ERest.message, "Haber Listeleme işlemi başarılı");
                hm.put(ERest.result, ls);
            }
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Haber Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }

    //category göre listeleme
    public Map<ERest, Object> newsCategoryList(String id){

        Map<ERest,Object> hm = new LinkedHashMap<>();

         try {

               int category_id = Integer.parseInt(id);
                List<News> ncat = nRepo.findCategory(category_id);


                hm.put(ERest.status,true);
                hm.put(ERest.message, "Haber Listeleme işlemi başarılı");
                hm.put(ERest.result, ncat);



        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Haber Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
}
