package com.ms.order.dtos;

import java.math.BigDecimal;

public record OrderItemDTO(String produto,
                           Integer quantidade,
                           BigDecimal preco) {
}
