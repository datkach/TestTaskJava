package com.teamvoy.testtaskjava.repository;

import com.teamvoy.testtaskjava.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByPaid(boolean b);
}
