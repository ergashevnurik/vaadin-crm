package com.vaadin.crm.backend.service;

import com.vaadin.crm.backend.entity.Lead;
import com.vaadin.crm.backend.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeadService {

    @Autowired
    private LeadRepository orderRepository;

    public List<Lead> findAll() {
        return orderRepository.findAll();
    }

    public List<Lead> findAll(String filterText) {
        if (filterText.isEmpty() || filterText == null) {
           return orderRepository.findAll();
        } else {
           return orderRepository.search(filterText);
        }
    }

    public void save(Lead order) {
        orderRepository.save(order);
    }

    public void delete(Lead order) {
        orderRepository.deleteById(order.getId());
    }

    public long count() {
        return orderRepository.count();
    }
}
