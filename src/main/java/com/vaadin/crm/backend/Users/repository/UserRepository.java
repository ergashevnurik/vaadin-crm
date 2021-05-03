package com.vaadin.crm.backend.Users.repository;

import com.vaadin.crm.backend.Users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select s from User s " + "where lower(s.username) like lower(concat('%', :filterText, '%')) "/* +
            "or lower(l.keywords) like lower(concat('%', :filterText, '%'))"*/)
    List<User> search(@Param("filterText")String filterText);
}
