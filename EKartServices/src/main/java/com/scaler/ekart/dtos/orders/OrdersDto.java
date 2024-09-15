package com.scaler.ekart.dtos.orders;

import com.scaler.ekart.models.Orders;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class OrdersDto {
    private int id;
    private int productId;
    private BigInteger quantity;
   public Orders toProduct() {
        Orders order = new Orders();
        order.setId(this.id);
        order.setProductId(this.productId);
        order.setQuantity(this.quantity);
        return order;
    }
}
