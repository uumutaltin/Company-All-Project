package com.works.dto;

import com.works.entities.Contents;
import com.works.entities.redis.ContentsRedis;
import com.works.repositories.ContentsRepository;
import com.works.repositories.redis.ContentsRedisRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContentsDto {

    final ContentsRedisRepository crRepo;
    final ContentsRepository cRepo;
    public ContentsDto(ContentsRedisRepository crRepo, ContentsRepository cRepo) {
        this.crRepo = crRepo;
        this.cRepo = cRepo;
    }


    // List - start
    public Map<ERest, Object> contentsList(String pageNo, String stpageSize){

        Map<ERest,Object> hm = new LinkedHashMap<>();

        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        try {

            if(pageSize == -1){
                List<ContentsRedis> lsx = new ArrayList<>();
                Iterable<ContentsRedis> page = crRepo.findAll();

                for (ContentsRedis item : page) {
                    lsx.add(item);
                }
                Collections.reverse(lsx);
                hm.put(ERest.status,true);
                hm.put(ERest.message, "İçerik Listeleme işlemi başarılı");
                hm.put(ERest.result, lsx);

            }else {
                Pageable pageable = PageRequest.of(ipageNumber, pageSize);
                List<ContentsRedis> ls = new ArrayList<>();
                Iterable<ContentsRedis> pageList = crRepo.findByOrderByCidDesc(pageable);

                for (ContentsRedis item : pageList){
                    ls.add(item);
                }

                hm.put(ERest.status,true);
                hm.put(ERest.message, "İçerik Listeleme işlemi başarılı");
                hm.put(ERest.result, ls);
            }
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"İçerik Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // List - end

    // Add - start
    public Map<ERest, Object> contentsAdd(Contents contents){
        Map<ERest,Object> hm = new LinkedHashMap<>();

        try {
            cRepo.save(contents);

            ContentsRedis contentsRedis = new ContentsRedis();
            contentsRedis.setCid(contents.getCid());
            contentsRedis.setCtitle(contents.getCtitle());
            contentsRedis.setCdescription(contents.getCdescription());
            contentsRedis.setCdetail(contents.getCdetail());
            contentsRedis.setCstatus(contents.getCstatus());
            contentsRedis.setCdate(contents.getCdate());

            crRepo.save(contentsRedis);

            hm.put(ERest.status, true);
            hm.put(ERest.message, "İçerik Ekleme başarılı");
            hm.put(ERest.result, contents );
        }catch (Exception e){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "İçerik ekleme sırasında bir hata oluştu!");

        }
        return hm;

    }
    // Add - end

    // Delete - start
    public Map<ERest, Object> contentsDelete(String strcid){
        Map<ERest, Object> hm = new LinkedHashMap<>();

        try {
            //redis delete - start
            ContentsRedis contentsRedis = crRepo.findById(strcid).get();

            int id = contentsRedis.getCid();
            crRepo.delete(contentsRedis);
            // redis delete - end

            // db delete - start
            cRepo.deleteById(id);
            // db delete - end

            hm.put(ERest.status, true);
            hm.put(ERest.message, "İçerik Silme başarılı");
            hm.put(ERest.result,strcid);



        }catch (Exception e){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "İçerik silme sırasında bir hata oluştu!");
            hm.put(ERest.result,strcid);

        }
        return hm;
    }
    // Delete - end





}
