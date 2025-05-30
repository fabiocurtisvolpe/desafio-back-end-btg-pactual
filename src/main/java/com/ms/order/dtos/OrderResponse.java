package com.ms.order.dtos;

import com.ms.order.models.OrderModel;

import java.math.BigDecimal;

public record OrderResponse(Long orderId,
                            Long customerId,
                            BigDecimal total) {

    public static OrderResponse fromModel(OrderModel model) {
        return new OrderResponse(model.getOrderId(), model.getCustomerId(), model.getTotal());
    }
}