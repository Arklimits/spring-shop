package com.arklimits.shop.order.repository;

import com.arklimits.shop.order.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT s FROM Order s JOIN FETCH s.member")
    List<Order> customFindAll();
}
