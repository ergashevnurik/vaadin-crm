package com.vaadin.crm.backend.Users.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER, EMPLOYEE, CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
