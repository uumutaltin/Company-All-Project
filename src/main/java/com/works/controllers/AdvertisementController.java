package com.works.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.works.documents.ElasticAdvertisement;
import com.works.repositories.elastic.ElasticSearchAdRepository;
import com.works.repositories.redis.AdvertisementRedisRepository;
import com.works.entities.Advertisement;
import com.works.repositories.AdvertisementRepository;
import com.works.entities.redis.AdvertisementRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/advertisement")
public class AdvertisementController {

    final private String UPLOAD_DIR = "src/main/resources/static/uploads/advertisement/";
    Integer searchSize;
    final AdvertisementRepository aRepo;
    final AdvertisementRedisRepository arRepo;
    final ElasticSearchAdRepository eaRepo;

    public AdvertisementController(AdvertisementRepository aRepo, AdvertisementRedisRepository arRepo, ElasticSearchAdRepository eaRepo) {
        this.aRepo = aRepo;
        this.arRepo = arRepo;
        this.eaRepo = eaRepo;
    }

    @GetMapping("")
    public String advertisement() {
        return "advertisement";
    }

    @GetMapping("/add")
    public String advertisementAdd(Model model) {
        model.addAttribute("advertisement", new Advertisement());
        return "advertisementAdd";
    }

    @GetMapping("/update/{straid}")
    public String advertisementUpdate(@PathVariable String straid, Model model) {
        AdvertisementRedis advertisementRedis = arRepo.findById(straid).get();
        model.addAttribute("advertisementUpdate", advertisementRedis);
        return "advertisementUpdate";
    }


    @PostMapping("/advertAdd")
    public String advertAdd(@Valid @ModelAttribute("advertisement") Advertisement advertisement, BindingResult bindingResult, @RequestParam("advertimage_file") MultipartFile file) {
        System.out.println("1: " + file.getOriginalFilename());
        System.out.println("2: " + StringUtils.cleanPath(file.getOriginalFilename()));
        String fileName = null;
        if (!file.getOriginalFilename().isEmpty()) {
            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = fileName.substring(fileName.length() - 4, fileName.length());

            String uui = UUID.randomUUID().toString();
            fileName = uui + ext;
            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {

            System.out.println(bindingResult);
            if (!bindingResult.hasErrors()) {

                advertisement.setAdvertimage(fileName);
                aRepo.save(advertisement);

                AdvertisementRedis adRedis = new AdvertisementRedis();
                adRedis.setAid(advertisement.getAid());
                adRedis.setAtitle(advertisement.getAtitle());
                adRedis.setAdesc(advertisement.getAdesc());
                adRedis.setDatestart(advertisement.getDatestart());
                adRedis.setDateend(advertisement.getDateend());
                adRedis.setAdvertimage(advertisement.getAdvertimage());
                adRedis.setAdvertwidth(advertisement.getAdvertwidth());
                adRedis.setAdvertheight(advertisement.getAdvertheight());
                adRedis.setAdvertlink(advertisement.getAdvertlink());

                arRepo.save(adRedis);


                return "redirect:/advertisement";

            }

        } catch (Exception e) {
            System.err.println("AdvertAdd Error : " + e);
        }


        return "advertisementAdd";


    }

    @ResponseBody
    @GetMapping("/advertList/{pageNo}/{stpageSize}")
    public List<AdvertisementRedis> advertList(@PathVariable String pageNo, @PathVariable String stpageSize) {

        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);

        if (pageSize == -1) {
            List<AdvertisementRedis> lsx = new ArrayList<>();
            Iterable<AdvertisementRedis> page = arRepo.findAll();
            List<Advertisement> adList =aRepo.findAll();
            eaRepo.deleteAll();
            for (AdvertisementRedis item : page) {
                lsx.add(item);
            }
            adList.forEach(item->{
                ElasticAdvertisement elasticAdvertisement = new ElasticAdvertisement();
                elasticAdvertisement.setAid(item.getAid());
                elasticAdvertisement.setAtitle(item.getAtitle());
                elasticAdvertisement.setAdesc(item.getAdesc());
                elasticAdvertisement.setDatestart(item.getDatestart());
                elasticAdvertisement.setDateend(item.getDateend());
                elasticAdvertisement.setAdvertimage(item.getAdvertimage());
                elasticAdvertisement.setAdvertwidth(item.getAdvertwidth());
                elasticAdvertisement.setAdvertheight(item.getAdvertheight());
                elasticAdvertisement.setAdvertlink(item.getAdvertlink());
                elasticAdvertisement.setAstatus(item.getAstatus());


                eaRepo.save(elasticAdvertisement);

            });

            Collections.reverse(lsx);
            return lsx;
        } else {
            Pageable pageable = PageRequest.of(ipageNumber, pageSize);
            Slice<AdvertisementRedis> pageList = arRepo.findByOrderByAidDesc(pageable);
            List<AdvertisementRedis> ls = pageList.getContent();
            List<Advertisement> adList =aRepo.findAll();
            eaRepo.deleteAll();

            for(Advertisement item : adList){
                ElasticAdvertisement elasticAdvertisement = new ElasticAdvertisement();
                elasticAdvertisement.setAid(item.getAid());
                elasticAdvertisement.setAtitle(item.getAtitle());
                elasticAdvertisement.setAdesc(item.getAdesc());
                elasticAdvertisement.setDatestart(item.getDatestart());
                elasticAdvertisement.setDateend(item.getDateend());
                elasticAdvertisement.setAdvertimage(item.getAdvertimage());
                elasticAdvertisement.setAdvertwidth(item.getAdvertwidth());
                elasticAdvertisement.setAdvertheight(item.getAdvertheight());
                elasticAdvertisement.setAdvertlink(item.getAdvertlink());
                elasticAdvertisement.setAstatus(item.getAstatus());


                eaRepo.save(elasticAdvertisement);
            }
            return ls;
        }

    }


    @ResponseBody
    @GetMapping("/delete/{straid}")
    public String advertDelete(@PathVariable String straid) {
        try {

            // redis delete - start
            AdvertisementRedis advertisementRedis = arRepo.findById(straid).get();
            String fileNameUpdate = advertisementRedis.getAdvertimage();
            File fileImage = new File(UPLOAD_DIR + fileNameUpdate);
            if (fileImage.exists()) {
                fileImage.delete();
            }

            int id = advertisementRedis.getAid();
            arRepo.delete(advertisementRedis);
            // redis delete - end

            // db delete - start
            aRepo.deleteById(id);
            // db delete - end

        } catch (Exception ex) {
            System.err.println("Ad Delete Error : " + ex);
        }
        return "advertisement";

    }


    @PostMapping("/advertUpdate/{straid}")
    public String advertUpdate(@Valid @ModelAttribute("advertisementUpdate") AdvertisementRedis adRedisUpdate, @PathVariable String straid, BindingResult bindingResult, @RequestParam("advertimage_file") MultipartFile file) {

        AdvertisementRedis adRedis = arRepo.findById(straid).get();


        System.out.println("1: " + file.getOriginalFilename());
        System.out.println("2: " + StringUtils.cleanPath(file.getOriginalFilename()));
        String fileName = null;
        if (!file.getOriginalFilename().isEmpty()) {

            fileName = StringUtils.cleanPath(file.getOriginalFilename());
            String ext = fileName.substring(fileName.length() - 4, fileName.length());

            String uui = UUID.randomUUID().toString();
            fileName = uui + ext;


            String fileNameUpdate = adRedis.getAdvertimage();
            File fileImage = new File(UPLOAD_DIR + fileNameUpdate);


            try {
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                if (fileImage.exists()) {
                    fileImage.delete();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileName = adRedis.getAdvertimage();

        }
        try {

            System.out.println(bindingResult);
            if (!bindingResult.hasErrors()) {

                adRedisUpdate.setAdvertimage(fileName);

                adRedisUpdate.setAid(adRedis.getAid());
                adRedisUpdate.setRaid(adRedis.getRaid());
                adRedisUpdate.setAdesc(adRedis.getAdesc());
                adRedisUpdate.setDatestart(adRedis.getDatestart());
                adRedisUpdate.setDateend(adRedis.getDateend());

                arRepo.save(adRedisUpdate);

                int id = adRedisUpdate.getAid();
                Advertisement advert = aRepo.findById(id).get();

                advert.setAid(adRedisUpdate.getAid());
                advert.setAtitle(adRedisUpdate.getAtitle());
                advert.setAdesc(adRedisUpdate.getAdesc());
                advert.setDatestart(adRedisUpdate.getDatestart());
                advert.setDateend(adRedisUpdate.getDateend());
                advert.setAdvertimage(adRedisUpdate.getAdvertimage());
                advert.setAdvertwidth(adRedisUpdate.getAdvertwidth());
                advert.setAdvertheight(adRedisUpdate.getAdvertheight());
                advert.setAdvertlink(adRedisUpdate.getAdvertlink());
                advert.setAstatus(adRedisUpdate.getAstatus());

                aRepo.save(advert);

                return "redirect:/advertisement";

            }


        } catch (Exception e) {
            System.err.println("Advertisement Update Error : " + e);
        }
        return "redirect:/advertisement/update/"+straid;

    }

    @JsonFormat(pattern="yyyy-MM-dd")
    @ResponseBody
    @GetMapping("/search/{pageNo}/{stpageSize}/{data}")
    public List<ElasticAdvertisement> advertSearch( @PathVariable String data, @PathVariable int pageNo,@PathVariable int stpageSize){
        System.out.println(data);
        System.out.println(pageNo);
        System.out.println(stpageSize);

        Page<ElasticAdvertisement> pages = eaRepo.findBySearch(data,PageRequest.of(pageNo,stpageSize));
        List<ElasticAdvertisement> list = pages.getContent();
        List<ElasticAdvertisement> list1 = eaRepo.find(data);
        searchSize = list1.size();
        System.out.println(list);
        return list ;


    }
    @ResponseBody
    @GetMapping("/advertList/pageCount/{stpageSize}/{stPageStatus}")
    public Integer pageCount(@PathVariable String stpageSize,@PathVariable String stPageStatus) {
        Integer pageStatus = Integer.parseInt(stPageStatus);
        long dataCount;
        if (pageStatus == 1) {
            dataCount = arRepo.count();
        }
        else{
            dataCount = searchSize;

        }
        double totalPageCount = Math.ceil((double)dataCount/Double.parseDouble(stpageSize));
        int pageCount = (int) totalPageCount;
        System.out.println("PageCount : " + pageCount);
        return pageCount;
    }

}