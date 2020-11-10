package com.vaadin.crm.backend.entity;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "order")
public class Order extends AbstractEntity implements Cloneable {

    public enum OrderStatus {
        Ordered, UnderConsideration, Canceled;
    }

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String keywords;

    @NotNull
    @NotEmpty
    private String link;

    @NotNull
    @NotEmpty
    private String brand;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Order.OrderStatus orderStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
