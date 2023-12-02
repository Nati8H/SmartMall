package com.ngsolutions.SmartMall.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Column
    @NotNull
    private String orderNumber;

    @Column
    private double discount;

    @Column
    private double price;

    @Column
    private double finalPrice;

    @ManyToOne
    private User user;
}
