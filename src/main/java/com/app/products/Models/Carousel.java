package com.app.products.Models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
public class Carousel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    public Carousel(){}

}
