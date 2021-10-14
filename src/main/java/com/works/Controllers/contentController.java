package com.works.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contents")
public class contentController {

    @GetMapping("")
    public String content(){
        return "contents";
    }

    @GetMapping("/add")
    public String contentAdd(){
        return "contentsAdd";
    }

    @GetMapping("/edit")
    public String contentUpdate(){
        return "contentsEdit";
    }



}
