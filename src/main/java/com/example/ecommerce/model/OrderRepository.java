package com.example.ecommerce.model;

import com.example.ecommerce.model.Order;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
