package com.vaadin.crm.backend.Orders.repository;

import com.vaadin.crm.backend.Orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
