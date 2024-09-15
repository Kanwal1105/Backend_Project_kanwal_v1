package com.scaler.ekart.repository;

import com.scaler.ekart.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

    public interface OrdersRepository extends JpaRepository<Orders, Long> {

        @Override
        Orders save(Orders o);

        List<Orders> findAll();
    }
