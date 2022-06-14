package com.app.products.Controllers;

import com.app.products.Models.Product;
import com.app.products.dto.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ShowViewController {
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(value="/addProduct", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnExampleView(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("addProduct");
       // mv.addObject("var","andrea");
        return mv;
    }


    @RequestMapping(value="/listProduct", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView returnListProduct(){
        List<Product> products = productRepository.findAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("listProducts");
        mv.addObject("products",products);
        return mv;
    }

}
