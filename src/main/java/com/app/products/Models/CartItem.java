package com.app.products.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name="cartitem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @Temporal(TemporalType.DATE)
    private Date date;
    //Many card items could have the same product
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id",nullable = false,updatable = false)
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id.equals(cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
