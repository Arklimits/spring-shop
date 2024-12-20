package com.arklimits.shop.domain.order.controller;

import com.arklimits.shop.domain.member.security.CustomUser;
import com.arklimits.shop.domain.order.entity.Order;
import com.arklimits.shop.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

        return "order/detail";
    }
}