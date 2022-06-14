package com.app.products.dto;

import com.app.products.Models.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarouselRepository extends JpaRepository<Carousel,Long> {


}
