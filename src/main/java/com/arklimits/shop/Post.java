package com.arklimits.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Date date;
}
