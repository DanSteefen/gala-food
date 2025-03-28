package com.oxo.gala_food.Gala_Food.service;

import com.oxo.gala_food.Gala_Food.request.OrderRequest;
import com.oxo.gala_food.Gala_Food.response.OrderResponse;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException;
    void verifyPayment(Map<String, String> paymentData, String status);
    List<OrderResponse> getUserOrders();
    void removeOrder(String orderId);
    List<OrderResponse> getOrdersOfAllUsers();
    void updateOrderStatus(String orderId, String status);
}
