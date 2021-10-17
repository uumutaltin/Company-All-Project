package com.works.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/survey")
public class SurveyController {


    @GetMapping("")
    public String survey(){
        return "survey";
    }

    @GetMapping("/add")
    public String surveyAdd(){
        return "surveyAdd";
    }

    @GetMapping("/detail")
    public String surveyDetail(){
        return "surveyDetail";
    }

    @GetMapping("/edit")
    public String surveyEdit(){
        return "surveyEdit";
    }
    @GetMapping("/option")
    public String surveyOption(){
        return "surveyOption";
    }



}
