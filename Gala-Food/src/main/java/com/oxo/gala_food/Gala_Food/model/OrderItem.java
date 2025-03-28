package com.oxo.gala_food.Gala_Food.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class OrderItem {

    private String foodId;
    private String qty;
    private String price;
    private String category;
    private String imageUrl;
    private String description;
    private String name;
}
