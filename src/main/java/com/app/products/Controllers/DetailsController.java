package com.app.products.Controllers;

import com.app.products.Models.Product;
import com.app.products.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DetailsController {

    @Autowired
    ProductService productService;
    @GetMapping("/details/{id}")
        public String showDetails(@PathVariable("id") Long id,
                Model model)
    {
        Product product = productService.getProductById(id);
        model.addAttribute("product",product);
        return "details";
    }
}
