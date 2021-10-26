package com.works.restcontrollers;

import com.works.dto.LikeDto;
import com.works.entities.restentity.UserLike;
import com.works.utils.ERest;
import com.works.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/like")
@Api(value ="LikeRestController",authorizations ={@Authorization(value = "basicAuth")})
public class LikeRestController {
    final Util util;
    final LikeDto lDto;

    public LikeRestController(Util util, LikeDto lDto) {
        this.util = util;
        this.lDto = lDto;
    }

    @GetMapping("/productLike/{productCount}/{point}/{cid}")
    public Map<ERest, Object> add(@PathVariable Integer productCount , @PathVariable Integer point ,@PathVariable Integer cid ){
        Map<ERest,Object> hm = new LinkedHashMap<>();

            return lDto.likeAdd(productCount,point,cid);

    }

}
