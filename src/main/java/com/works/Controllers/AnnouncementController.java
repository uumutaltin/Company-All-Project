package com.works.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {


    @GetMapping("")
    public String announcement(){
        return "announcement";
    }

    @GetMapping("/add")
    public String announcementAdd(){
        return "announcementAdd";
    }

    @GetMapping("/edit")
    public String announcementEdit(){
        return "announcementEdit";
    }

}
