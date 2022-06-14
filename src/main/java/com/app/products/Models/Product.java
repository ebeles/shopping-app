package com.app.products.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data //this is from Lombok to generate setters and getters
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int price;
    private String brand;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Discount discount;

    private int quantity;

    @Lob
    @Column(columnDefinition="MEDIUMBLOB")
    private String image;
    //private String category;

    @ManyToMany(cascade =CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="product_category",joinColumns = {
            @JoinColumn(name="product_id", referencedColumnName = "id")
    }, inverseJoinColumns = {@JoinColumn(name="category_id",referencedColumnName = "id")})
    private Set<Category> categories =new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Set<Carousel> carousel;
}
