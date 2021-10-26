package com.works.controllers;

import com.works.entities.projection.OrdersJoin;
import com.works.entities.restentity.Orders;
import com.works.repositories.OrdersRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersManagerController {
    final OrdersRepository oRepo;

    public OrdersManagerController(OrdersRepository oRepo) {
        this.oRepo = oRepo;
    }

    @GetMapping("")
    public String orders(Model model) {
        List<Orders> ls = oRepo.orderList();
        model.addAttribute("orderList",ls);

        return "ordersManager";
    }

    @GetMapping("/delete/{id}")
    public String ordersDelete(Model model, @PathVariable Integer id) {
        Integer deleteid = oRepo.deleteOrder(id);
        return "redirect:/orders";
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(Model model, @PathVariable Integer id) {
        List<OrdersJoin> ls  = oRepo.orderDetail(id);
        Integer totalPrice = 0;
        Integer cid = 0;
        model.addAttribute("productList",ls);
        for (OrdersJoin o : ls){
            totalPrice+= o.getPrice();
            cid = o.getCustomerid();

        }
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("customerid",cid);
        return "orderDetail";
    }
    @GetMapping("/complete/{id}")
    public String orderComplete(Model model, @PathVariable Integer id) {
    oRepo.orderComplete(id);

    return "redirect:/orders";
    }
}
