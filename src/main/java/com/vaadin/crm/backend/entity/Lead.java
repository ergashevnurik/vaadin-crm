package com.vaadin.crm.backend.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "EdsOrder")
public class Lead extends AbstractEntity implements Cloneable {

    public enum OrderStatus {
        Ordered, UnderConsideration, Canceled;
    }

    @NotNull
    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Lead.OrderStatus orderStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
