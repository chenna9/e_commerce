package org.jsp.controller;

import org.jsp.dto.AddToCartRequest;
import org.jsp.dto.CartItem;
import org.jsp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItem> addToCart(
            @RequestBody AddToCartRequest request) {

        CartItem cartItem = cartService.addToCart(request);

        return ResponseEntity.ok(cartItem);
    }
}