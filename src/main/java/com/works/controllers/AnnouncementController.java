package com.works.controllers;

import com.works.documents.ElasticAnnouncement;
import com.works.entities.Announcement;
import com.works.entities.redis.AnnouncementRedis;
import com.works.repositories.AnnouncementRepository;
import com.works.repositories.elastic.ElasticSearchAnnouncementRepository;
import com.works.repositories.redis.AnnouncementRedisRepository;
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
import java.util.List;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

    Integer searchSize;
    final AnnouncementRepository aRepo;
    final AnnouncementRedisRepository arRepo;
    final ElasticSearchAnnouncementRepository eaRepo;
    public AnnouncementController(AnnouncementRepository aRepo, AnnouncementRedisRepository arRepo, ElasticSearchAnnouncementRepository eaRepo) {
        this.aRepo = aRepo;
        this.arRepo = arRepo;
        this.eaRepo = eaRepo;
    }


    //------------ html - start ------------
    @GetMapping("")
    public String announcement(){
        return "announcement";
    }

    @GetMapping("/add")
    public String announcementAdd(Model model){
        model.addAttribute("announcement",new Announcement());
        return "announcementAdd";
    }

    @GetMapping("/edit")
    public String announcementEdit(){
        return "announcementEdit";
    }
    //------------ html - end ------------

    //------------ mvc - start ------------

    // Add - start
    @PostMapping("/announcementAdd")
    public String announcementAdd(@Valid @ModelAttribute("announcement")Announcement announcement, BindingResult bindingResult){

        try {
            System.out.println(bindingResult);
            if(!bindingResult.hasErrors()){

                aRepo.save(announcement);
                AnnouncementRedis announcementRedis = new AnnouncementRedis();
                announcementRedis.setAid(announcement.getAid());
                announcementRedis.setAtitle(announcement.getAtitle());
                announcementRedis.setAdetail(announcement.getAdetail());
                announcementRedis.setAstatus(announcement.getAstatus());
                announcementRedis.setAdate(announcement.getAdate());

                arRepo.save(announcementRedis);
                return "redirect:/announcement";
            }


        }catch (Exception e){
            System.err.println("AnnouncementAdd Error : " + e);
        }
        return "announcementAdd";
    }
    // Add - end

    // List - start
    @ResponseBody
    @GetMapping("/announcementList/{pageNo}/{stpageSize}")
    public List<AnnouncementRedis> announcementList(@PathVariable String pageNo, @PathVariable String stpageSize){
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        if (pageSize == -1){
            List<AnnouncementRedis> lsx = new ArrayList<>();
            Iterable<AnnouncementRedis> page = arRepo.findAll();
            List<Announcement> announcementList = aRepo.findAll();
            eaRepo.deleteAll();
            for (AnnouncementRedis item : page){
                lsx.add(item);
            }
            announcementList.forEach((item ->{
                ElasticAnnouncement elasticAnnouncement = new ElasticAnnouncement();
                elasticAnnouncement.setAid(item.getAid());
                elasticAnnouncement.setAtitle(item.getAtitle());
                elasticAnnouncement.setAdetail(item.getAdetail());
                elasticAnnouncement.setAstatus(item.getAstatus());
                elasticAnnouncement.setAdate(item.getAdate());

                eaRepo.save(elasticAnnouncement);
            }));
            Collections.reverse(lsx);
            return lsx;
        }else {
            Pageable pageable = PageRequest.of(ipageNumber, pageSize);
            Slice<AnnouncementRedis> pageList = arRepo.findByOrderByAidDesc(pageable);
            List<AnnouncementRedis> ls = pageList.getContent();
            List<Announcement> announcementList = aRepo.findAll();
            eaRepo.deleteAll();
            for (Announcement item : announcementList){
                ElasticAnnouncement elasticAnnouncement = new ElasticAnnouncement();
                elasticAnnouncement.setAid(item.getAid());
                elasticAnnouncement.setAtitle(item.getAtitle());
                elasticAnnouncement.setAdetail(item.getAdetail());
                elasticAnnouncement.setAstatus(item.getAstatus());
                elasticAnnouncement.setAdate(item.getAdate());

                eaRepo.save(elasticAnnouncement);
            }
            return ls;
        }
    }
    // List - end





    //------------ mvc - end ------------

}
