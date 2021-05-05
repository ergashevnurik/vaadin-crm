package com.vaadin.crm.backend.Orders.repository;

import com.vaadin.crm.backend.Orders.entity.Order;
import com.vaadin.crm.backend.Products.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o " + "where lower(o.id) like lower(concat('%', :filterText, '%')) "/* +
            "or lower(l.keywords) like lower(concat('%', :filterText, '%'))"*/)
    List<Order> search(@Param("filterText")String filterText);
}
