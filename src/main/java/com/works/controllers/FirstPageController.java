package com.works.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class FirstPageController {

    @GetMapping("")
    public String firstpage() {
        return "firstPage";
    }


}
