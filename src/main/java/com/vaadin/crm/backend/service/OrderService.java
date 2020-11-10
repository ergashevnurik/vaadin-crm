package com.vaadin.crm.backend.service;

import com.vaadin.crm.backend.entity.Order;
import com.vaadin.crm.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findAll(String filterText) {
        if (filterText.isEmpty() || filterText == null) {
           return orderRepository.findAll();
        } else {
           return orderRepository.search(filterText);
        }
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void delete(Order order) {
        orderRepository.deleteById(order.getId());
    }

    public long count() {
        return orderRepository.count();
    }
}
