package com.works.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticContents;
import com.works.entities.Contents;
import com.works.entities.redis.ContentsRedis;
import com.works.repositories.ContentsRepository;
import com.works.repositories.elastic.ElasticSearchContentsRepository;
import com.works.repositories.redis.ContentsRedisRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/contents")
public class ContentController {

    Integer searchSize;
    final ContentsRepository cRepo;
    final ContentsRedisRepository crRepo;
    final ElasticSearchContentsRepository ecRepo;
    public ContentController(ContentsRepository cRepo, ContentsRedisRepository crRepo, ElasticSearchContentsRepository ecRepo) {
        this.cRepo = cRepo;
        this.crRepo = crRepo;
        this.ecRepo = ecRepo;
    }

    // html - start
    @GetMapping("")
    public String content(){
        return "contents";
    }

    @GetMapping("/add")
    public String contentAdd(Model model){
        model.addAttribute("contents", new Contents());
        return "contentsAdd";
    }

    @GetMapping("/update/{strcid}")
    public String contentUpdate(@PathVariable String strcid, Model model){
        ContentsRedis contentsRedis = crRepo.findById(strcid).get();
        model.addAttribute("contentUpdate",contentsRedis);
        return "contentsEdit";
    }

    // html - end

    // mvc - start

    @PostMapping("/contentsAdd")
    public String contentsAdd(@Valid @ModelAttribute("contents")Contents contents, BindingResult bindingResult){

        System.out.println("********************Contents Add e girdi************************");

        try {
            System.out.println(bindingResult);
            if(!bindingResult.hasErrors()){

                cRepo.save(contents);

                System.out.println("************************************"+ contents.getCdescription());
                ContentsRedis contentsRedis = new ContentsRedis();
                contentsRedis.setCid(contents.getCid());
                contentsRedis.setCtitle(contents.getCtitle());
                contentsRedis.setCdescription(contents.getCdescription());
                contentsRedis.setCdetail(contents.getCdetail());
                contentsRedis.setCstatus(contents.getCstatus());
                contentsRedis.setCdate(contents.getCdate());

                crRepo.save(contentsRedis);

                return "redirect:/contents";
            }

        }catch (Exception e){
            System.err.println("ContentsAdd Error : " + e);
        }
        return "contentsAdd";
    }


    // for cStatus

    @ResponseBody
    @GetMapping("/contentList/{pageNo}/{stpageSize}/{cStatus}")
    public List<Contents> contentsListStatus(@PathVariable String pageNo, @PathVariable String stpageSize, @PathVariable String cStatus){
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);
        int status = Integer.parseInt(cStatus);

        if(status == 1) {
            if (pageSize == -1) {
                List<Contents> lsx = new ArrayList<>();

                List<Contents> contentsList = cRepo.findByCstatusIsTrueOrderByCidDesc();
                ecRepo.deleteAll();
                for (Contents item : contentsList) {
                    lsx.add(item);
                }
                contentsList.forEach(item -> {
                    ElasticContents elasticContents = new ElasticContents();
                    elasticContents.setCid(item.getCid());
                    elasticContents.setCtitle(item.getCtitle());
                    elasticContents.setCdescription(item.getCdescription());
                    elasticContents.setCdetail(item.getCdetail());
                    elasticContents.setCstatus(item.getCstatus());
                    elasticContents.setCdate(item.getCdate());

                    ecRepo.save(elasticContents);
                });
                return lsx;
            } else {
                Pageable pageable = PageRequest.of(ipageNumber, pageSize);
                List<Contents> ls = cRepo.findByCstatusIsTrue(pageable);

                ecRepo.deleteAll();
                for (Contents item : ls) {
                    ElasticContents elasticContents = new ElasticContents();
                    elasticContents.setCid(item.getCid());
                    elasticContents.setCtitle(item.getCtitle());
                    elasticContents.setCdescription(item.getCdescription());
                    elasticContents.setCdetail(item.getCdetail());
                    elasticContents.setCstatus(item.getCstatus());
                    elasticContents.setCdate(item.getCdate());

                    ecRepo.save(elasticContents);

                }
                return ls;
            }
        }
        else {
            if (pageSize == -1) {
                List<Contents> lsx = new ArrayList<>();

                List<Contents> contentsList = cRepo.findByCstatusIsFalseOrderByCidDesc();
                ecRepo.deleteAll();
                for (Contents item : contentsList) {
                    lsx.add(item);
                }
                contentsList.forEach(item -> {
                    ElasticContents elasticContents = new ElasticContents();
                    elasticContents.setCid(item.getCid());
                    elasticContents.setCtitle(item.getCtitle());
                    elasticContents.setCdescription(item.getCdescription());
                    elasticContents.setCdetail(item.getCdetail());
                    elasticContents.setCstatus(item.getCstatus());
                    elasticContents.setCdate(item.getCdate());

                    ecRepo.save(elasticContents);
                });
                return lsx;
            } else {
                Pageable pageable = PageRequest.of(ipageNumber, pageSize);
                List<Contents> ls = cRepo.findByCstatusIsFalse(pageable);

                ecRepo.deleteAll();
                for (Contents item : ls) {
                    ElasticContents elasticContents = new ElasticContents();
                    elasticContents.setCid(item.getCid());
                    elasticContents.setCtitle(item.getCtitle());
                    elasticContents.setCdescription(item.getCdescription());
                    elasticContents.setCdetail(item.getCdetail());
                    elasticContents.setCstatus(item.getCstatus());
                    elasticContents.setCdate(item.getCdate());

                    ecRepo.save(elasticContents);

                }
                return ls;
            }
        }

    }
    // for cStatus





    @ResponseBody
    @GetMapping("/contentList/{pageNo}/{stpageSize}")
    public List<ContentsRedis> contentsList(@PathVariable String pageNo, @PathVariable String stpageSize){
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);


        if (pageSize == -1) {
            List<ContentsRedis> lsx = new ArrayList<>();
            Iterable<ContentsRedis> page = crRepo.findAll();
            List<Contents> contentsList = cRepo.findAll();
            ecRepo.deleteAll();
            for (ContentsRedis item : page) {
                lsx.add(item);
            }
            contentsList.forEach(item -> {
                ElasticContents elasticContents = new ElasticContents();
                elasticContents.setCid(item.getCid());
                elasticContents.setCtitle(item.getCtitle());
                elasticContents.setCdescription(item.getCdescription());
                elasticContents.setCdetail(item.getCdetail());
                elasticContents.setCstatus(item.getCstatus());
                elasticContents.setCdate(item.getCdate());

                ecRepo.save(elasticContents);
            });
            Collections.reverse(lsx);
            return lsx;
        } else {
            Pageable pageable = PageRequest.of(ipageNumber, pageSize);
            Slice<ContentsRedis> pageList = crRepo.findByOrderByCidDesc(pageable);
            List<ContentsRedis> ls = pageList.getContent();
            List<Contents> contentsList = cRepo.findAll();
            ecRepo.deleteAll();
            for (Contents item : contentsList) {
                ElasticContents elasticContents = new ElasticContents();
                elasticContents.setCid(item.getCid());
                elasticContents.setCtitle(item.getCtitle());
                elasticContents.setCdescription(item.getCdescription());
                elasticContents.setCdetail(item.getCdetail());
                elasticContents.setCstatus(item.getCstatus());
                elasticContents.setCdate(item.getCdate());

                ecRepo.save(elasticContents);

            }
            return ls;
        }


    }

    @ResponseBody
    @GetMapping("/delete/{strcid}")
    public String contentDelete(@PathVariable String strcid){
        try{

            //redis delete - start
            ContentsRedis contentsRedis = crRepo.findById(strcid).get();

            int id = contentsRedis.getCid();
            crRepo.delete(contentsRedis);
            // redis delete - end

            // db delete - start
            cRepo.deleteById(id);
            // db delete - end

        } catch (Exception e){
            System.err.println("Contents Delete Error : " + e );
        }
        return "contents";


    }

    @PostMapping("/contentUpdate/{strcid}")
    public String contentUpdate(@Valid @ModelAttribute("contentUpdate")ContentsRedis contentsRedisUpdate, @PathVariable String strcid, BindingResult bindingResult ){

        ContentsRedis contentsRedis = crRepo.findById(strcid).get();

        try {

            if(!bindingResult.hasErrors()){
                contentsRedisUpdate.setRcid(contentsRedis.getRcid());
                contentsRedisUpdate.setCid(contentsRedis.getCid());
                contentsRedisUpdate.setCdate(contentsRedis.getCdate());



                crRepo.save(contentsRedisUpdate);

                System.out.println("Update Redis title : "+contentsRedisUpdate.getCtitle());

                int id = contentsRedisUpdate.getCid();
                Contents contents = cRepo.findById(id).get();

                contents.setCid(contentsRedisUpdate.getCid());
                contents.setCtitle(contentsRedisUpdate.getCtitle());
                contents.setCdescription(contentsRedisUpdate.getCdescription());
                contents.setCdetail(contentsRedisUpdate.getCdetail());
                contents.setCstatus(contentsRedisUpdate.getCstatus());
                contents.setCdate(contentsRedisUpdate.getCdate());


                cRepo.save(contents);

                System.out.println("Update db title : "+contents.getCtitle());


                return "redirect:/contents";
            }



        }catch (Exception e){
            System.err.println("Content Update Error : " + e);
        }
        return "redirect:/contents/update/"+strcid;

    }

    @JsonFormat(pattern="yyyy-MM-dd")
    @ResponseBody
    @GetMapping("/search/{pageNo}/{stpageSize}/{data}")
    public List<ElasticContents> contentSearch( @PathVariable String data, @PathVariable int pageNo,@PathVariable int stpageSize ){

        Page<ElasticContents> pages = ecRepo.findBySearch(data,PageRequest.of(pageNo,stpageSize));
        List<ElasticContents> list = pages.getContent();
        List<ElasticContents> list1 = ecRepo.find(data);
        searchSize = list1.size();
        return list;
    }

    @ResponseBody
    @GetMapping("/contentList/pageCount/{stpageSize}/{stPageStatus}")
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



    //mvc - end


}
