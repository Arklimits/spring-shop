package com.arklimits.shop.order.controller;

import com.arklimits.shop.member.security.CustomUser;
import com.arklimits.shop.order.entity.Order;
import com.arklimits.shop.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/purchase")
    public String purchaseItem(@RequestParam String title, @RequestParam Integer price,
        @RequestParam Integer quantity, @RequestParam String imageUrl,
        Authentication authentication, Model model) {

        CustomUser user = (CustomUser) authentication.getPrincipal();

        Order order = orderService.saveOrder(title, price, quantity, user, imageUrl);
        model.addAttribute("order", order);

        return "orderDetail";
    }

    @GetMapping("/order/all")
    public String getOrderAll() {
        List<Order> result = orderService.findAll();
        System.out.println(result);

        return "index";
    }
}