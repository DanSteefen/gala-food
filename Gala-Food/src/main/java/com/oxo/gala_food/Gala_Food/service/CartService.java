package com.oxo.gala_food.Gala_Food.service;

import com.oxo.gala_food.Gala_Food.request.CartRequest;
import com.oxo.gala_food.Gala_Food.response.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest request);
    CartResponse getCart();
    void clearCart();
    CartResponse removeFromCart(CartRequest request);
}
