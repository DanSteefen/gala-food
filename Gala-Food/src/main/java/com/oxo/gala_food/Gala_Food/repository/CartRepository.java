package com.oxo.gala_food.Gala_Food.repository;

import com.oxo.gala_food.Gala_Food.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserId(String userId);
    void deleteByUserId(String userId);
}
