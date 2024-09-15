package com.scaler.ekart.services.OrderServices;

import com.scaler.ekart.models.Orders;
import com.scaler.ekart.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dbOrdersService")
public class OrderServiceDBImpl implements OrdersService {
    private OrdersRepository orderRepository;

    public OrderServiceDBImpl(OrdersRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Orders createOrder(Orders ord) {
        Orders createdOrders = orderRepository.save(ord);
        System.out.println("Order created");

        return createdOrders;
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
}
