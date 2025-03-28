package com.oxo.gala_food.Gala_Food.controler;

import com.oxo.gala_food.Gala_Food.request.OrderRequest;
import com.oxo.gala_food.Gala_Food.response.OrderResponse;
import com.oxo.gala_food.Gala_Food.service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request) throws RazorpayException {

        OrderResponse response = orderService.createOrderWithPayment(request);
        return response;
    }

    @PostMapping("/verify")
    public void verifyPayment(@RequestBody Map<String, String> paymentData){
        orderService.verifyPayment(paymentData, "paid");
    }

    @GetMapping
    public List<OrderResponse> getUserOrders(){
        return orderService.getUserOrders();
    }

    @DeleteMapping("/{orderID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrder(@PathVariable String orderId){
        orderService.removeOrder(orderId);
    }

    @GetMapping("/all")
    public List<OrderResponse> getOrdersOfAllUsers(){
        return orderService.getOrdersOfAllUsers();
    }

    @PatchMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId, @RequestParam String status){
        orderService.updateOrderStatus(orderId, status);
    }
}
