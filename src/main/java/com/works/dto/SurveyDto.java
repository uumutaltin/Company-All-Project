package com.works.dto;

import com.works.entities.Options;
import com.works.entities.redis.SurveyRedis;
import com.works.entities.restentity.SurveyOptionRest;
import com.works.repositories.OptionsRepository;
import com.works.repositories.SurveyOptionRestRepository;
import com.works.repositories.SurveyRepository;
import com.works.repositories.redis.SurveyRedisRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SurveyDto {

    final SurveyRepository sRepo;
    final SurveyRedisRepository srRepo;
    final SurveyOptionRestRepository sorRepo;
    final OptionsRepository oRepo;
    public SurveyDto(SurveyRepository sRepo, SurveyRedisRepository srRepo, SurveyOptionRestRepository sorRepo, OptionsRepository oRepo) {
        this.sRepo = sRepo;
        this.srRepo = srRepo;
        this.sorRepo = sorRepo;
        this.oRepo = oRepo;
    }

    // List - start
    public Map<ERest, Object> surveyList(String pageNo, String stpageSize){

        Map<ERest,Object> hm = new LinkedHashMap<>();

        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        try {
            if(pageSize == -1){
                List<SurveyRedis> lsx = new ArrayList<>();
                Iterable<SurveyRedis> page = srRepo.findAll();

                for ( SurveyRedis item : page){
                    lsx.add(item);
                }
                Collections.reverse(lsx);
                hm.put(ERest.status,true);
                hm.put(ERest.message, "Anket Listeleme işlemi başarılı");
                hm.put(ERest.result, lsx);
            }else {
                Pageable pageable = PageRequest.of(ipageNumber, pageSize);
                List<SurveyRedis> ls = new ArrayList<>();
                Iterable<SurveyRedis> pageList = srRepo.findByOrderBySidDesc(pageable);

                for (SurveyRedis item : pageList){
                    ls.add(item);
                }

                hm.put(ERest.status,true);
                hm.put(ERest.message, "Anket Listeleme işlemi başarılı");
                hm.put(ERest.result, ls);
            }

        }catch (Exception e){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Anket Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // List - end

    // Option Vote - start
    public Map<ERest, Object> optionVote(String stsaid,String stsocid , String stsooid,String stsosid){

        Map<ERest,Object> hm = new LinkedHashMap<>();

        int socid = Integer.parseInt(stsocid);
        int sooid = Integer.parseInt(stsooid);
        int sosid = Integer.parseInt(stsosid);

        try {
            SurveyOptionRest surveyOptionRest = new SurveyOptionRest();


            surveyOptionRest.setSocid(socid);
            surveyOptionRest.setSooid(sooid);
            surveyOptionRest.setSoid(sosid);

            Options options = oRepo.findById(sooid).get();
            options.setVoteNumber( options.getVoteNumber() + 1 );

            oRepo.saveAndFlush(options);

            SurveyRedis surveyRedis = srRepo.findById(stsaid).get();

            surveyRedis.getOptions().forEach(item ->{
                if(item.getOid() == sooid){
                    item.setVoteNumber(options.getVoteNumber());
                }
            });
            srRepo.save(surveyRedis);
            sorRepo.save(surveyOptionRest);


            hm.put(ERest.status,true);
            hm.put(ERest.message, "Anket oylama işlemi başarılı");
            hm.put(ERest.result, options);

        }catch (Exception e){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Anket oylama işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // Option Vote - end


}
