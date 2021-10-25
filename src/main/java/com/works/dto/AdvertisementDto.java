package com.works.dto;

import com.works.entities.Advertisement;
import com.works.entities.redis.AdvertisementRedis;
import com.works.layers.AdvertisementInterLayer;
import com.works.repositories.AdvertisementRepository;
import com.works.repositories.redis.AdvertisementRedisRepository;
import com.works.utils.ERest;
import com.works.utils.Util;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdvertisementDto {

    final private String UPLOAD_DIR = "src/main/resources/static/uploads/advertisement/";
    final AdvertisementRepository aRepo;
    final AdvertisementRedisRepository arRepo;
    final Util util;

    public AdvertisementDto(AdvertisementRepository aRepo, AdvertisementRedisRepository arRepo, Util util) {
        this.aRepo = aRepo;
        this.arRepo = arRepo;
        this.util = util;
    }

    public Map<ERest,Object> advertAdd( AdvertisementInterLayer advertisementInterLayer, MultipartFile file) {

        String errorMessage = "";
        Map<ERest, Object> hm = new LinkedHashMap<>();
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

                Advertisement ad = new Advertisement();
                ad.setAdvertimage(fileName);
                ad.setAtitle(advertisementInterLayer.getAtitle());
                ad.setAdesc(advertisementInterLayer.getAdesc());
                ad.setAdvertheight(advertisementInterLayer.getAdvertheight());
                ad.setAdvertwidth(advertisementInterLayer.getAdvertwidth());
                SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date datestart = date.parse(advertisementInterLayer.getDatestart());
                Date dateend = date.parse(advertisementInterLayer.getDateend());
                ad.setDateend(dateend);
                ad.setDatestart(datestart);
                ad.setAdvertlink(advertisementInterLayer.getAdvertlink());

                aRepo.save(ad);

                AdvertisementRedis adRedis = new AdvertisementRedis();

                hm.put(ERest.status, true);
                hm.put(ERest.message, "Ekleme başarılı");
                hm.put(ERest.result, ad );

                adRedis.setAtitle(advertisementInterLayer.getAtitle());
                adRedis.setAid(ad.getAid());
                adRedis.setAdesc(advertisementInterLayer.getAdesc());
                adRedis.setDatestart(datestart);
                adRedis.setDateend(dateend);
                adRedis.setAdvertimage(fileName);
                adRedis.setAdvertwidth(advertisementInterLayer.getAdvertwidth());
                adRedis.setAdvertheight(advertisementInterLayer.getAdvertheight());
                adRedis.setAdvertlink(advertisementInterLayer.getAdvertlink());

                arRepo.save(adRedis);

        } catch (Exception ex) {
            hm.put(ERest.status, false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, " HATA!");
            }
        }



        return hm;





    }


    public Map<ERest,Object> advList(String pageNo, String stpageSize){
        Map<ERest ,Object> hm=new LinkedHashMap<>();
        int ipageNumber = Integer.parseInt(pageNo);
        int pageSize = Integer.parseInt(stpageSize);
        try {

            if(pageSize == -1){
                List<AdvertisementRedis> lsx = new ArrayList<>();
                Iterable<AdvertisementRedis> page = arRepo.findAll();

                for (AdvertisementRedis item : page) {
                    lsx.add(item);
                }
                Collections.reverse(lsx);
                hm.put(ERest.status,true);
                hm.put(ERest.message, "İçerik Listeleme işlemi başarılı");
                hm.put(ERest.result, lsx);

            }else {
                Pageable pageable = PageRequest.of(ipageNumber, pageSize);
                List<AdvertisementRedis> ls = new ArrayList<>();
                Iterable<AdvertisementRedis> pageList = arRepo.findByOrderByAidDesc(pageable);

                for (AdvertisementRedis item : pageList){
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


    public Map<ERest,Object> advertDelete( String straid) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {

            // redis delete - start
            AdvertisementRedis advertisementRedis = arRepo.findById(straid).get();
            String fileNameUpdate = advertisementRedis.getAdvertimage();
            File fileImage = new File(UPLOAD_DIR + fileNameUpdate);
            if (fileImage.exists()) {
                fileImage.delete();
            }

            int id = advertisementRedis.getAid();
            System.out.println("iddddddddddd"+ id);
            arRepo.delete(advertisementRedis);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Silme başarılı");
            // redis delete - end

            // db delete - start
            aRepo.deleteById(id);
            // db delete - end

        } catch (Exception ex) {
            System.err.println("Ad Delete Error : " + ex);
        }
        return hm;

    }




}
