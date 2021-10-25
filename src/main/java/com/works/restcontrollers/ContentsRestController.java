package com.works.restcontrollers;

import com.works.dto.ContentsDto;
import com.works.entities.Contents;
import com.works.utils.ERest;
import com.works.utils.Util;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contents")
public class ContentsRestController {

    final ContentsDto cDto;
    final Util util;
    public ContentsRestController(ContentsDto cDto, Util util) {
        this.cDto = cDto;
        this.util = util;
    }

    // List - start
    @GetMapping("/contentList/{pageNo}/{stpageSize}")
    public Map<ERest, Object> contentsListRest(@PathVariable String pageNo, @PathVariable String stpageSize){
        return cDto.contentsList(pageNo,stpageSize);
    }
    // List - end

    // Add - start
    @PostMapping("/contentsAdd")
    public Map<ERest, Object> contentsAddRest(@Valid @RequestBody Contents contents, BindingResult bindingResult ){
        Map<ERest, Object> hm = new LinkedHashMap<>();

        System.out.println("contents.getCtitle() "+ contents.getCtitle());
        System.out.println("contents.getCdescription() "+ contents.getCdescription());
        System.out.println("contents.getCdetail() "+ contents.getCdetail());

        if(bindingResult.hasErrors()){
            hm.put(ERest.status, false);
            hm.put(ERest.errors, util.errors(bindingResult));
            return hm;
        }else {
            return cDto.contentsAdd(contents);
        }
    }
    // Add - end


    // Delete - start
    @DeleteMapping("/contentsDelete/{strcid}")
    public Map<ERest, Object> contentsDelete(@PathVariable String strcid){
        return cDto.contentsDelete(strcid);
    }
    // Delete - end

    // Update - start


    // Update - end



}
