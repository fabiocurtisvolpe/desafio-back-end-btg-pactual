package com.ms.order.consumers;

import com.ms.order.dtos.OrderCreatedDTO;
import com.ms.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

@Component
public class OrderCreatedConsumer {

    private final Logger logger = LoggerFactory.getLogger(OrderCreatedConsumer.class);

    private final OrderService orderService;

    public OrderCreatedConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "${broker.queue.ms.name}" )
    public void listenOrderQueue(Message<OrderCreatedDTO> message) {
        logger.info("Message consumed: {}", message);

        orderService.save(message.getPayload());
    }
}
