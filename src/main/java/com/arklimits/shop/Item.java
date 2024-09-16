package com.arklimits.shop;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public Integer price;
}
