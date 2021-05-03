package com.vaadin.crm.backend.Products.repository;

import com.vaadin.crm.backend.Products.entity.Products;
import com.vaadin.crm.backend.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Products, Long> {
    @Query("select p from Products p " + "where lower(p.productName) like lower(concat('%', :filterText, '%')) "/* +
            "or lower(l.keywords) like lower(concat('%', :filterText, '%'))"*/)
    List<Products> search(@Param("filterText")String filterText);
}
