package com.app.products.dto;

import com.app.products.Models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    //session token must be unique
    ShoppingCart findBySessionToken(String sessionToken);
}
