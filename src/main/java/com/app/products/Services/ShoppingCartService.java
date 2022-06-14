package com.app.products.Services;

import com.app.products.Models.CartItem;
import com.app.products.Models.Product;
import com.app.products.Models.ShoppingCart;
import com.app.products.dto.CartItemRepository;
import com.app.products.dto.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    CartItemRepository cartItemRepository;


    public ShoppingCart addShoppingCartFirst(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setDate(new Date());
        cartItem.setProduct(productService.getProductById(id));
        shoppingCart.getItems().add(cartItem);
        shoppingCart.setSessionToken((sessionToken));
        shoppingCart.setDate(new Date());
        return shoppingCartRepository.save(shoppingCart);
    //TODO maybe change to ShoppingCart instead of void if there is a problem and add return

    }

    public ShoppingCart addToExistingShoppingCart(Long id, String sessionToken, int quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        //adding a product to a shoppingcart
        Product p = productService.getProductById(id);
        //what if the product is already there, and we are adding it again
        // in this case update a quantity of the product, do not add a new product
        Boolean productExistInCart = false;
        if(shoppingCart !=null) {
            Set<CartItem> items = shoppingCart.getItems();
            for (CartItem item : items) {
                System.out.println(item.getProduct().getId());
                if(item.getProduct().equals(p)){
                    productExistInCart = true;
                    item.setQuantity(item.getQuantity() + quantity);
                    shoppingCart.setItems(items);
                    return shoppingCartRepository.saveAndFlush(shoppingCart);
                }
//                if (p.getId().equals(item.getProduct().getId())) {
//                    item.setQuantity(item.getQuantity() + quantity);
//                    return shoppingCartRepository.save(shoppingCart);
//
//                }
            }
        }
        if(!productExistInCart && (shoppingCart != null)) {

            CartItem cartItem1 = new CartItem();
            cartItem1.setDate(new Date());
            cartItem1.setQuantity(quantity);
            cartItem1.setProduct(p);
            shoppingCart.getItems().add(cartItem1);
            return shoppingCartRepository.saveAndFlush(shoppingCart);
        }

        return this.addShoppingCartFirst(id,sessionToken,quantity);
    }


    public ShoppingCart getShoppingCartBySessionToken(String sessionToken) {
    return shoppingCartRepository.findBySessionToken(sessionToken);
    }

    //This is used when user adds news quantity directly in the shopping cart instead of the one she/he added before. We will update the database like this with new quantity of the product in the cart
    public CartItem updateShoppingCartItem(Long id, int quantity) {
        CartItem cartItem = cartItemRepository.findById(id).get();
        cartItem.setQuantity(quantity);
       return cartItemRepository.saveAndFlush(cartItem);
    }

    public ShoppingCart removeCartItemFromShoppingCart(Long id, String sessionToken) {
        ShoppingCart shoppingCart = shoppingCartRepository.findBySessionToken(sessionToken);
        Set<CartItem> items = shoppingCart.getItems();
        CartItem cartItem = null;
        for(CartItem item: items){
            if(item.getId() == id){
                cartItem = item;
            }
        }
        items.remove(cartItem);
        cartItemRepository.delete(cartItem);
        shoppingCart.setItems(items);
        //System.out.println("got here in the service");
        return shoppingCartRepository.save(shoppingCart);

    }
}
