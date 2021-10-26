package com.works.controllers;

import com.works.entities.*;
import com.works.entities.restentity.Orders;
import com.works.entities.restentity.UserLike;
import com.works.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    final ProductRepository pRepo;
    final LikeRepository lRepo;
    final ContentsRepository cRepo;
    final NewsRepository nRepo;
    final OrdersRepository oRepo;
    final CustomerRepository cuRepo;
    final SurveyRepository sRepo;
    final AnnouncementRepository aRepo;
    public HomeController(ProductRepository pRepo, LikeRepository lRepo, ContentsRepository cRepo, NewsRepository nRepo, OrdersRepository oRepo, CustomerRepository cuRepo, SurveyRepository sRepo, AnnouncementRepository aRepo) {
        this.pRepo = pRepo;
        this.lRepo = lRepo;
        this.cRepo = cRepo;
        this.nRepo = nRepo;
        this.oRepo = oRepo;
        this.cuRepo = cuRepo;
        this.sRepo = sRepo;
        this.aRepo = aRepo;
    }


    @GetMapping("")
    public String home(Model model) {
        List<Product> productList = pRepo.findAll();
        List<UserLike> userLikeList = lRepo.findAll();
        List<Contents> contentsList = cRepo.findAll();
        List<News> activeNews = nRepo.activeNews();
        List<News> passiveNews = nRepo.passiveNews();
        List<News> newsList = nRepo.findAll();
        List<Orders> ordersList = oRepo.findAll();
        List<Customer> customerList = cuRepo.findAll();
        List<Survey> surveyList = sRepo.findAll();
        List<Announcement> announcementList = aRepo.findAll();

        model.addAttribute("productList",productList);
        model.addAttribute("userLikeList",userLikeList);
        model.addAttribute("contentsList",contentsList);
        model.addAttribute("activeNews",activeNews);
        model.addAttribute("passiveNews",passiveNews);
        model.addAttribute("newsList",newsList);
        model.addAttribute("ordersList",ordersList);
        model.addAttribute("customerList",customerList);
        model.addAttribute("surveyList",surveyList);
        model.addAttribute("announcementList",announcementList);
        return "home";
    }
}
