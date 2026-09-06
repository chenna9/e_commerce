package org.jsp.service;

import java.time.LocalDateTime;

import org.jsp.dto.AddToCartRequest;
import org.jsp.dto.Cart;
import org.jsp.dto.CartItem;
import org.jsp.dto.Product;
import org.jsp.dto.User;
import org.jsp.repository.CartItemRepository;
import org.jsp.repository.CartRepository;
import org.jsp.repository.ProductRepo;
import org.jsp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private CartItemRepository cartItemRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepo productRepo;


    public CartItem addToCart(AddToCartRequest request) {

        // 1. Find User
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // 2. Find Product
        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        // 3. Check requested quantity against stock
        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        // 4. Find existing cart or create a new cart
        Cart cart = cartRepo.findByUserId(user.getId())
                .orElseGet(() -> {

                    Cart newCart = new Cart();

                    newCart.setUser(user);
                    newCart.setCreatedDate(LocalDateTime.now());

                    return cartRepo.save(newCart);
                });

        // 5. Check whether product already exists in cart
        CartItem cartItem = cartItemRepo
                .findByCartIdAndProductId(
                        cart.getId(),
                        product.getId()
                )
                .orElse(null);

        // 6. Product already exists → increase quantity
        if (cartItem != null) {

            int newQuantity =
                    cartItem.getQuantity() + request.getQuantity();

            // Check total quantity against stock
            if (newQuantity > product.getStock()) {
                throw new RuntimeException("Insufficient stock");
            }

            cartItem.setQuantity(newQuantity);

        } 
        
        // 7. Product doesn't exist → create CartItem
        else {

            cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }

        // 8. Save CartItem
        return cartItemRepo.save(cartItem);
    }
}