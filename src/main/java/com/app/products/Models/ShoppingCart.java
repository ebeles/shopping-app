package com.app.products.Models;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@Table(name="shoppingcart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Transient
    private Double totalPrice;
    @Transient
    private int itemsNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CartItem> items = new HashSet<>();

    private String sessionToken;

    public ShoppingCart()
    {}

    public Double getTotalPrice() {
        Double sum= 0.0;
        for(CartItem item : this.items)
        {
            sum += item.getProduct().getPrice() * item.getQuantity();
        }
        return sum;
    }

    public int getItemsNumber(){



        return this.items.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return id.equals(that.id) && Objects.equals(items, that.items) && Objects.equals(sessionToken, that.sessionToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items, sessionToken);
    }
}
