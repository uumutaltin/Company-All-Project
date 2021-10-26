package com.works.restcontrollers;

import com.works.dto.SurveyDto;
import com.works.utils.ERest;
import com.works.utils.Util;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/survey")
public class SurveyRestController {

    final SurveyDto sDto;
    final Util util;
    public SurveyRestController(SurveyDto sDto, Util util) {
        this.sDto = sDto;
        this.util = util;
    }

    // List - start
    @GetMapping("/surveyList/{pageNo}/{stpageSize}")
    public Map<ERest, Object> surveyListRest(@PathVariable String pageNo, @PathVariable String stpageSize){
        return sDto.surveyList(pageNo,stpageSize);
    }
    // List - end

    // Option Vote - start
    @PutMapping("/optionsVote/{stsaid}/{stsocid}/{stsooid}/{stsosid}")
    public Map<ERest, Object> optionsVote(@PathVariable String stsaid, @PathVariable String stsocid, @PathVariable String stsooid, @PathVariable String stsosid ){
        return sDto.optionVote(stsaid,stsocid,stsooid,stsosid);
    }
    // Option Vote - end

}
