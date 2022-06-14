package com.app.products.Models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @Transient
    private int productsNumber;
    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "categories")
    private Set<Product> products;


public Category(){}

    public int getProductsNumber() {

    return this.products.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id) && name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
