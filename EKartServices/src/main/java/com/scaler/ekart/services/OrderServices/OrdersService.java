package com.scaler.ekart.services.OrderServices;

import com.scaler.ekart.models.Orders;

import java.util.List;

public interface OrdersService {
    Orders createOrder(Orders product);

    List<Orders> getAllOrders();
}
