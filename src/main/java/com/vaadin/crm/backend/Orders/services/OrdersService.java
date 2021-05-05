package com.vaadin.crm.backend.Orders.services;

import com.vaadin.crm.backend.Orders.entity.Order;
import com.vaadin.crm.backend.Orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public List<Order> getAllOrders(String value) {
        if (value != null && !value.isEmpty()) {
            return orderRepository.search(value);
        } else {
            return orderRepository.findAll();
        }
    }
}
