package com.oxo.gala_food.Gala_Food.service.impl;

import com.oxo.gala_food.Gala_Food.model.Cart;
import com.oxo.gala_food.Gala_Food.repository.CartRepository;
import com.oxo.gala_food.Gala_Food.request.CartRequest;
import com.oxo.gala_food.Gala_Food.response.CartResponse;
import com.oxo.gala_food.Gala_Food.service.CartService;
import com.oxo.gala_food.Gala_Food.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserService userService;

    @Override
    public CartResponse addToCart(CartRequest request) {

        String loggedInUserId = userService.findByUserId();
        Optional<Cart> cartOptional = cartRepository.findByUserId(loggedInUserId);
        Cart cart = cartOptional.orElseGet(() -> new Cart(loggedInUserId, new HashMap<>()));
        Map<String, Integer> cartItems = cart.getItems();
        cartItems.put(request.getFoodId(), cartItems.getOrDefault(request.getFoodId(), 0) + 1);
        cart.setItems(cartItems);
        cart = cartRepository.save(cart);
        return convertToResponse(cart);
    }

    @Override
    public CartResponse getCart() {

        String loggedInUserId = userService.findByUserId();
        Cart cart = cartRepository.findByUserId(loggedInUserId).orElse(new Cart(null, loggedInUserId, new HashMap<>()));
        return convertToResponse(cart);
    }

    @Override
    public void clearCart() {

        String loggedInUserId = userService.findByUserId();
        cartRepository.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest request) {

        String loggedInUserId = userService.findByUserId();
        Cart cart = cartRepository.findByUserId(loggedInUserId).orElseThrow(() -> new RuntimeException("Cart is not found.."));
        Map<String, Integer> cartItems = cart.getItems();
        if (cartItems.containsKey(request.getFoodId())){
            int currentQty = cartItems.get(request.getFoodId());
            if (currentQty > 0){
                cartItems.put(request.getFoodId(), currentQty - 1);
            } else {
                cartItems.remove(request.getFoodId());
            }
            cart = cartRepository.save(cart);
        }
         return convertToResponse(cart);
    }

    private CartResponse convertToResponse(Cart cart){
        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUserId())
                .items(cart.getItems())
                .build();
    }
}
