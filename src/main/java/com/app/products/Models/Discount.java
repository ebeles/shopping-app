package com.app.products.Models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int discount;
    @Transient
    private boolean isDiscount;

    private boolean isDiscount(){
        if(this.discount != 0)
            return true;
        return false;
    }

}
