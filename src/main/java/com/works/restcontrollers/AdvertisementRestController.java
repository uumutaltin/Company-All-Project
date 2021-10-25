package com.works.restcontrollers;

import com.works.dto.AdvertisementDto;
import com.works.entities.Advertisement;
import com.works.layers.AdvertisementInterLayer;
import com.works.repositories.redis.AdvertisementRedisRepository;
import com.works.utils.ERest;
import com.works.utils.Util;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/advertisement")
public class AdvertisementRestController {

    final AdvertisementDto aDto;
    final Util util;
    final AdvertisementRedisRepository arRepo;

    public AdvertisementRestController(AdvertisementDto aDto, Util util, AdvertisementRedisRepository arRepo) {
        this.aDto = aDto;
        this.util = util;
        this.arRepo = arRepo;
    }

    @PostMapping("/advertAdd")
    public Map<ERest, Object> advertAdd(@Valid AdvertisementInterLayer advertisement, BindingResult bindingResult, @RequestParam("advertimage_file") MultipartFile file) {
        Map<ERest,Object> hm = new LinkedHashMap<>();

        if(bindingResult.hasErrors()){
            hm.put(ERest.status, false);
            hm.put(ERest.errors, util.errors(bindingResult) );
            return hm;
        }else{
            return  aDto.advertAdd(advertisement,file);
        }
    }

    @ApiOperation("Reklam veri listeleme")
    @GetMapping("/advertList/{pageNo}/{stpageSize}")
    public Map<ERest, Object> contentsListRest(@PathVariable String pageNo, @PathVariable String stpageSize){
        return aDto.advList(pageNo,stpageSize);
    }

    @ApiOperation("Reklam veri silme")
    @DeleteMapping("/advertDelete/{strlid}")
    public Map<ERest, Object> advDelete (@PathVariable String strlid) {
        return aDto.advertDelete(strlid);
    }




}
