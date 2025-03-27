package com.oxo.gala_food.Gala_Food.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    private String foodId;
//    private String userId;
//    private Map<String, Integer> items = new HashMap<>();
}
