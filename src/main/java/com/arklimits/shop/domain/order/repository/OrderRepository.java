package com.arklimits.shop.domain.order.repository;

import com.arklimits.shop.domain.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByMember_Id(Long memberId);

    @Query(value = "SELECT s FROM Order s JOIN FETCH s.member")
    List<Order> customFindAll();
}
