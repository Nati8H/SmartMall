package com.ngsolutions.SmartMall.model.entity;

import jakarta.persistence.*;

import java.util.Currency;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private byte[] photo;

    @JoinColumn(name = "categoryId")
    @ManyToOne
    private Category category;

    @Column(name = "currencyId")
    private Currency currency;
}
