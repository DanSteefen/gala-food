package com.oxo.gala_food.Gala_Food.repository;

import com.oxo.gala_food.Gala_Food.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {
}
