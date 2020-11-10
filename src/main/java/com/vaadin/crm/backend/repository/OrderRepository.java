package com.vaadin.crm.backend.repository;

import com.vaadin.crm.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o " + "where lower(o.name) like lower(concat('%', :filterText, '%')) " +
            "or lower(o.keywords) like lower(concat('%', :filterText, '%'))")
    List<Order> search(@Param("filterText")String filterText);
}
