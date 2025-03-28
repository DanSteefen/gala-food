package com.oxo.gala_food.Gala_Food.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;
    private List<OrderItem> orderItems;
    private double amount;
    private String paymentStatus;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    private String orderStatus;
}
