package com.ms.order.dtos;

import java.util.List;

public record OrderCreatedDTO(Long codigoPedido,
                              Long codigoCliente,
                              List<OrderItemDTO> itens) {
}
