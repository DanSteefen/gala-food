package com.oxo.gala_food.Gala_Food.service.impl;

import com.oxo.gala_food.Gala_Food.model.Order;
import com.oxo.gala_food.Gala_Food.repository.CartRepository;
import com.oxo.gala_food.Gala_Food.repository.OrderRepository;
import com.oxo.gala_food.Gala_Food.request.OrderRequest;
import com.oxo.gala_food.Gala_Food.response.OrderResponse;
import com.oxo.gala_food.Gala_Food.service.OrderService;
import com.oxo.gala_food.Gala_Food.service.UserService;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CartRepository cartRepository;

    @Value("${razorpay_key}")
    private String RAZORPAY_KEY;
    @Value("${razorpay_secret}")
    private String RAZORPAY_SECRET;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {

        Order newOrder = convertToEntity(request);
        newOrder = orderRepository.save(newOrder);

        //create razorpay payment
        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", newOrder.getAmount());
        orderRequest.put("currency", "USD");
        orderRequest.put("payment_capture", 1);

        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(orderRequest);
        newOrder.setRazorpayOrderId(razorpayOrder.get("id"));
        String loggedInUser = userService.findByUserId();
        newOrder.setUserId(loggedInUser);
        newOrder = orderRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    @Override
    public void verifyPayment(Map<String, String> paymentData, String status) {

        String razorpayOrderId = paymentData.get("razorpay_order_id");
        Order existingOrder = orderRepository.findByRazorpayOrderId(razorpayOrderId).orElseThrow(() -> new RuntimeException("Payment not found.."));
        existingOrder.setPaymentStatus(status);
        existingOrder.setRazorpaySignature(paymentData.get("razorpay_signature"));
        existingOrder.setRazorpayPaymentId(paymentData.get("razorpay_payment_id"));
        orderRepository.save(existingOrder);
        if ("paid".equalsIgnoreCase(status)){
            cartRepository.deleteByUserId(existingOrder.getUserId());
        }
    }

    @Override
    public List<OrderResponse> getUserOrders() {

        String loggedInUserId = userService.findByUserId();
        List<Order> list = orderRepository.findByUserId(loggedInUserId);
        return list.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void removeOrder(String orderId) {

        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {

        List<Order> list = orderRepository.findAll();
        return list.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {

        Order entity = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found.."));
        entity.setOrderStatus(status);
        orderRepository.save(entity);
    }

    private OrderResponse convertToResponse(Order newOrder) {
        return OrderResponse.builder()
                .id(newOrder.getId())
                .amount(newOrder.getAmount())
                .userAddress(newOrder.getUserAddress())
                .userId(newOrder.getUserId())
                .email(newOrder.getEmail())
                .phoneNumber(newOrder.getPhoneNumber())
                .razorpayOrderId(newOrder.getRazorpayOrderId())
                .paymentStatus(newOrder.getPaymentStatus())
                .orderStatus(newOrder.getOrderStatus())
                .orderItems(newOrder.getOrderItems())
                .build();
    }

    private Order convertToEntity(OrderRequest request){
        return Order.builder()
                .userAddress(request.getUserAddress())
                .amount(request.getAmount())
                .orderItems(request.getOrderItems())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .orderStatus(request.getOrderStatus())
                .build();
    }
}
