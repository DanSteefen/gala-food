package com.oxo.gala_food.Gala_Food.repository;

import com.oxo.gala_food.Gala_Food.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);
    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);
}
