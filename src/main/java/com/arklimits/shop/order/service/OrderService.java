package com.arklimits.shop.order.service;

import com.arklimits.shop.member.entity.Member;
import com.arklimits.shop.member.security.CustomUser;
import com.arklimits.shop.order.entity.Order;
import com.arklimits.shop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order saveOrder(String title, Integer price, Integer quantity, CustomUser user,
        String imageUrl) {
        Member member = new Member();
        member.setId(user.getId());
        member.setDisplayName(user.getDisplayName());

        Order order = new Order(title, price, quantity, member, imageUrl);
        orderRepository.save(order);

        return order;
    }
}
