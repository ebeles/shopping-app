package com.app.products.Controllers;

import com.app.products.Models.ShoppingCart;
import com.app.products.Services.ProductService;
import com.app.products.Services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GeneralController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ProductService productService;



    @ModelAttribute
    public void populateTheModel(Model model, HttpServletRequest request)
    {
        String sessionToken = (String) request.getSession(true).getAttribute("sessionToken");
        if(sessionToken == null){
            model.addAttribute("shoppingCart", new ShoppingCart());
        }else{
            model.addAttribute("shoppingCart", shoppingCartService.getShoppingCartBySessionToken(sessionToken));
        }
        model.addAttribute("categories", productService.getAllCategories());
        model.addAttribute("brands", productService.getAllBrands());

    }

}
