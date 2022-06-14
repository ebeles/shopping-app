package com.app.products.Controllers;

import com.app.products.Models.Product;
import com.app.products.Services.ProductService;
import com.app.products.Services.ShoppingCartService;
import com.app.products.dto.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Controller
public class IndexController {

   @Autowired
   private ProductService productService;

   @Autowired
  private ShoppingCartService shoppingCartService;

   @Autowired
   private CategoryRepository categoryRepository;

   @GetMapping("/")
    public String showIndex(Model model) {
       model.addAttribute("products", productService.getAllProduct());
//       model.addAttribute(("categories"), productService.getAllCategories());
       return "index2.html";
   }

   @GetMapping("/{id}")
   public String viewByCategory(Model model, @PathVariable Integer id) {
       model.addAttribute("categories", categoryRepository.findAll());
       return "index2";
   }

   @PostMapping("/addToCart")
   public String addToCart(HttpServletRequest request, Model model,
                           @RequestParam("id") Long id,
                           @RequestParam("quantity") int quantity)
   {
       //If this is the first time creating a cart we need to create the session, bcs we are tracking the shopping carts with the session
      String sessionToken =(String) request.getSession(true).getAttribute("sessionToken");
      if(sessionToken == null){
         sessionToken = UUID.randomUUID().toString();
          request.getSession().setAttribute("sessionToken", sessionToken);
          shoppingCartService.addShoppingCartFirst(id,sessionToken,quantity);
      } else{
         shoppingCartService.addToExistingShoppingCart(id,sessionToken,quantity);
      }
      return "redirect:/";
   }

   @GetMapping("/shoppingCart")
   public String showShoppingCartView(HttpServletRequest request,Model model)
   { //we delete it here bcs it is in the GeneralController
//       String sessionToken =(String) request.getSession(true).getAttribute("sessionToken");
//      if(sessionToken == null){
//          model.addAttribute("shoppingCart",new ShoppingCart());
//      }else{
//          ShoppingCart shoppingCart = shoppingCartService.getShoppingCartBySessionToken(sessionToken);
//            model.addAttribute("shoppingCart",shoppingCart); //this is in the shoppingcart.html the th:each="cartItem: ${shoppingCart.items}"
//      }

       return "shoppingCart.html";
   }

   @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Long id, Model model)
   {
       Product product = productService.getProductById(id);
       model.addAttribute("product",product);
       return "details";
   }

   @PostMapping("/updateShoppingCart")
    public String updateCartItem(@RequestParam("item_id") Long id,
                                 @RequestParam("quantity") int quantity)
   {
       shoppingCartService.updateShoppingCartItem(id,quantity);
        return "redirect:/shoppingCart.html";
   }

   @GetMapping("/removeCartItem/{id}")
    public String removeItemFromShoppingCart(@PathVariable("id") Long id,
                                             HttpServletRequest request)
   {
       String sessionToken = (String) request.getSession(false).getAttribute("sessionToken");

       shoppingCartService.removeCartItemFromShoppingCart(id,sessionToken);

       return "redirect:/shoppingCart";
   }

   @RequestMapping(value="/search",method = RequestMethod.GET)
   @ResponseBody
   public ModelAndView search(@RequestParam("value") String value)
   {
       ModelAndView mv = new ModelAndView();
       mv.setViewName("searchFragment");
       List<Product> products = productService.searchProductByName(value);
       mv.addObject("products", products);
       return mv;
   }








}
