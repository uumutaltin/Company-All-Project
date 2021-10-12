package com.works.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {


    @GetMapping("")
    public String news(){
        return "news";
    }

    @GetMapping("/add")
    public String newsAdd(){
        return "newsAdd";
    }

    @GetMapping("/category")
    public String newsCategory(){
        return "newsCategory";
    }



}
