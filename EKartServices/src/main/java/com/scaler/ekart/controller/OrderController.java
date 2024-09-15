package com.scaler.ekart.controller;

import com.scaler.ekart.models.Orders;
import com.scaler.ekart.services.OrderServices.OrdersService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Orders")

public class OrderController {
    private OrdersService orderService;

    public OrderController(@Qualifier("dbOrdersService") OrdersService ordService) {
        this.orderService = ordService;
    }
    @GetMapping("/getOrders")
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/createOrder")
    public Orders createOrder(@RequestBody Orders order) {
        return orderService.createOrder(order);
    }

}
