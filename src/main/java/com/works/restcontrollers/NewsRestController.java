package com.works.restcontrollers;

import com.works.dto.NewsDto;
import com.works.utils.ERest;
import com.works.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/news")
@Api(value ="NewsRestController",authorizations ={@Authorization(value = "basicAuth")})
public class NewsRestController {
    final NewsDto nDto;
    final Util util;

    public NewsRestController(NewsDto nDto, Util util) {
        this.nDto = nDto;
        this.util = util;
    }
    @GetMapping("/newsList/{pageNo}/{stpageSize}")
    public Map<ERest, Object> contentsListRest(@PathVariable String pageNo, @PathVariable String stpageSize){
        return nDto.newsList(pageNo,stpageSize);
    }

    @GetMapping("/newsCList/{stId}")
    public Map<ERest, Object> newsCategoryList(@PathVariable String stId) {
        return  nDto.newsCategoryList(stId);
    }

}
