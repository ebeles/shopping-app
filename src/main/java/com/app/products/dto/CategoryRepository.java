package com.app.products.dto;

import com.app.products.Models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

     Category findByName(String name); // it will work becouse the String name is the same as the string name in the Category model class
}
