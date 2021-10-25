package com.works.controllers;

import com.works.entities.projection.LikeJoin;
import com.works.entities.projection.ProductLike;
import com.works.repositories.LikeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/likes")
public class LikesController {

    final LikeRepository lRepo;

    public LikesController(LikeRepository lRepo) {
        this.lRepo = lRepo;
    }

    @GetMapping("")
    public String likes(Model model){
        List<LikeJoin> ls = lRepo.allLikes();
        List<ProductLike> lsx = lRepo.productsLikes();

        model.addAttribute("allLikes",ls);
        model.addAttribute("productLike",lsx);

        return "likes";

    }


}
