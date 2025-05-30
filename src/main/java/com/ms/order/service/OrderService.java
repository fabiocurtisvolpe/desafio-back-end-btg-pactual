package com.ms.order.service;

import com.ms.order.dtos.OrderCreatedDTO;
import com.ms.order.dtos.OrderResponse;
import com.ms.order.models.ItemModel;
import com.ms.order.models.OrderModel;
import com.ms.order.repository.OrderRepository;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MongoTemplate mongoTemplate;

    public OrderService(OrderRepository orderRepository, MongoTemplate mongoTemplate) {
        this.orderRepository = orderRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public void save(OrderCreatedDTO dto) {

        var model = new OrderModel();

        model.setOrderId(dto.codigoPedido());
        model.setCustomerId(dto.codigoCliente());
        model.setItems(getOrderItems(dto));
        model.setTotal(getTotal(dto));

        orderRepository.save(model);
    }

    public Page<OrderResponse> findAllByCustomerId(Long customerId, PageRequest pageRequest) {
        var orders = orderRepository.findAllByCustomerId(customerId, pageRequest);

        return orders.map(OrderResponse::fromModel);
    }

    public BigDecimal findTotalOnOrdersByCustomerId(Long customerId) {
        var aggregations = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("customerId").is(customerId)),
                Aggregation.group().sum("total").as("total")
        );

        AggregationResults<Document> response = mongoTemplate.aggregate(aggregations, "tb_orders", Document.class);
        Document uniqueResult = response.getUniqueMappedResult();

        if (uniqueResult == null) {
            return BigDecimal.ZERO;
        }

        Object totalValue = uniqueResult.getOrDefault("total", BigDecimal.ZERO);

        return new BigDecimal(totalValue.toString());
    }

    protected BigDecimal getTotal(OrderCreatedDTO dto) {
        return dto.itens()
                .stream()
                .map(i -> i.preco().multiply(BigDecimal.valueOf(i.quantidade())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    protected static List<ItemModel> getOrderItems(OrderCreatedDTO dto) {
        return dto.itens().stream()
                .map(i -> new ItemModel(i.produto(), i.quantidade(), i.preco()))
                .toList();
    }
}
