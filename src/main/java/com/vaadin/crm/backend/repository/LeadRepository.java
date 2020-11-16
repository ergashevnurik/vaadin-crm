package com.vaadin.crm.backend.repository;

import com.vaadin.crm.backend.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    @Query("select l from Lead l " + "where lower(l.name) like lower(concat('%', :filterText, '%')) "/* +
            "or lower(l.keywords) like lower(concat('%', :filterText, '%'))"*/)
    List<Lead> search(@Param("filterText")String filterText);
}
