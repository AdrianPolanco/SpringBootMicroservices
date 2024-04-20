package com.adriandev.orders.services;

import com.adriandev.orders.model.dtos.OrderItemRequest;
import com.adriandev.orders.model.dtos.OrderRequest;
import com.adriandev.orders.model.entities.Order;
import com.adriandev.orders.model.entities.OrderItems;
import com.adriandev.orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){

        //Verificar si hay stock
        this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderRequest.getOrderItems())
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderItems(orderRequest.getOrderItems().stream().map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order)).toList());

        this.orderRepository.save(order);
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }
}
