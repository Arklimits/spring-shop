package com.arklimits.shop.domain.order.service;

import com.arklimits.shop.domain.member.entity.Member;
import com.arklimits.shop.domain.member.repository.MemberRepository;
import com.arklimits.shop.domain.member.security.CustomUser;
import com.arklimits.shop.domain.order.dto.OrderDTO;
import com.arklimits.shop.domain.order.entity.Order;
import com.arklimits.shop.domain.order.repository.OrderRepository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public List<OrderDTO> findAll() {
        List<Order> orders = orderRepository.customFindAll();

        return orders.stream().map(order -> new OrderDTO(
            order.getId(),
            order.getTitle(),
            order.getPrice(),
            order.getQuantity(),
            order.getMember().getDisplayName(),
            order.getImageUrl(),
            order.getOrderDate()
        )).collect(Collectors.toList());
    }

    public List<OrderDTO> findOrdersByMemberId(Long id) {
        List<Order> orders = orderRepository.findByMember_Id(id);

        return orders.stream()
            .map(order -> new OrderDTO(
                order.getId(),
                order.getTitle(),
                order.getPrice(),
                order.getQuantity(),
                order.getMember().getDisplayName(),
                order.getImageUrl(),
                order.getOrderDate()
            ))
            .sorted(Comparator.comparing(OrderDTO::getOrderDate).reversed())
            .collect(Collectors.toList());
    }

    public Order saveOrder(String title, Integer price, Integer quantity, CustomUser user,
        String imageUrl) {
        Member member = memberRepository.findById(user.getId())
            .orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + user.getId()));

        Order order = new Order(title, price, quantity, member, imageUrl);
        orderRepository.save(order);

        return order;
    }
}
