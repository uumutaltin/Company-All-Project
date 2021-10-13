package com.works.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gallery")
public class GalleryController {

    @GetMapping("")
    public String gallery() {
        return "gallery";
    }

    @GetMapping("/property")
    public String galleryproperty() {
        return "galleryPropertyAdd";
    }

    @GetMapping("/property/edit")
    public String gallerypropertyEdit() {
        return "galleryPropertyEdit";
    }
}
