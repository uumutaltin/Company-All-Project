package com.works.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/advertisement")
public class advertisementController {

    @GetMapping("")
    public String advertisement(){
        return "advertisement";
    }

    @GetMapping("/add")
    public String advertisementAdd(){
        return "advertisementAdd";
    }
}
