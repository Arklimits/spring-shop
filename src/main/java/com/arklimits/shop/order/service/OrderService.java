package com.arklimits.shop.order.service;

import com.arklimits.shop.order.entity.Order;
import com.arklimits.shop.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order saveOrder(String title, Integer price, Integer quantity, Long memberId,
        String imageUrl) {

        Order order = new Order(title, price, quantity, memberId, imageUrl);
        orderRepository.save(order);

        return order;
    }
}
