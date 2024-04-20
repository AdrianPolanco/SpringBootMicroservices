package com.adriandev.orders.repositories;

import com.adriandev.orders.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
