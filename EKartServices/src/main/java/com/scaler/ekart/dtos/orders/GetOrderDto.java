package com.scaler.ekart.dtos.orders;

import com.scaler.ekart.models.Orders;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class GetOrderDto {
    private int id;
    private int productId;
    private BigInteger quantity;
           public GetOrderDto from(Orders order) {

               var orderResponseDto = new GetOrderDto();
            orderResponseDto.setId(order.id);
            orderResponseDto.setProductId(order.productId);
            orderResponseDto.setQuantity(order.quantity);

            return orderResponseDto;
        }
}
