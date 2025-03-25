package com.oxo.gala_food.Gala_Food.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "foods")
public class Food {

    @Id
    private String id;
    private String name;
    private String description;
    private String price;
    private String category;
    private String imageUrl;
}
