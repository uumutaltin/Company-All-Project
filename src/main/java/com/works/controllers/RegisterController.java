package com.works.controllers;

import com.works.entities.Role;
import com.works.entities.Users;
import com.works.repositories.RoleRepository;
import com.works.repositories.UserRepository;
import com.works.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {

    final UserRepository uRepo;
    final RoleRepository rRepo;
    final UserService uService;
    public RegisterController(UserRepository uRepo, RoleRepository rRepo, UserService uService) {
        this.uRepo = uRepo;
        this.rRepo = rRepo;
        this.uService = uService;
    }

    // html - start
    @GetMapping("")
    public String register(Model model){
        model.addAttribute("user",new Users());
        return "register";
    }
    // html - end

    @PostMapping("/userRegister")
    public String register(@Valid @ModelAttribute("user") Users user, @RequestParam(defaultValue = "1")String roleIDSt , BindingResult bindingResult){

        try{

            if(!bindingResult.hasErrors()){
                int roleID = Integer.parseInt(roleIDSt);
                user.setEnabled(true);
                user.setTokenExpired(true);

                Role role= rRepo.findById(roleID).get();
                List<Role> roles=new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);

                uService.register(user);
                return "redirect:/login";
            }

        }catch (Exception e){
            System.out.println(e);
        }
        return "register";
    }
}



