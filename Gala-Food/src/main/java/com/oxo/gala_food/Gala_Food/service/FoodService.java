package com.oxo.gala_food.Gala_Food.service;

import com.oxo.gala_food.Gala_Food.request.FoodRequest;
import com.oxo.gala_food.Gala_Food.response.FoodResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodService {

    String uploadFile(MultipartFile file);
    FoodResponse addFood(FoodRequest request, MultipartFile file);
    List<FoodResponse> readFoods();
    FoodResponse readFood(String id);
    boolean deleteFile(String fileName);
    void deleteFood(String id);
}
