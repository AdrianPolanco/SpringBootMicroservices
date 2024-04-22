package com.adriandev.orders.services;

import com.adriandev.orders.model.dtos.*;
import com.adriandev.orders.model.entities.Order;
import com.adriandev.orders.model.entities.OrderItems;
import com.adriandev.orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        /*El record BaseResponse recibe un String[] errorMessages por el constructor, Spring WebFlux
        que es la libreria que estoy usando para hacer la solicitud, tiene una libreria llamada Jackson
        que se encarga de serializar y deserializar automaticamente la respuesta JSON a la clase que le especifico
        (en este caso BaseResponse), si en la respuesta JSON existe una propiedad llamada errorMessages, la
        pasara automaticamente al constructor de esa instancia que estoy obteniendo en BaseResponse.class, sin
        necesidad de yo hacerlo manualmente*/
        //Verificar si hay stock
        BaseResponse result = this.webClientBuilder.build()
                .post() //HTTP Method
                .uri("http://localhost:8083/api/inventory/in-stock") //Route that will be fetched
                .bodyValue(orderRequest.getOrderItems()) //Request body
                .retrieve() //Execution of the request
                .bodyToMono(BaseResponse.class)//Converting the response to a Mono, that is a Publisher that emits one or zero elements
                //Void.class is used to indicate that it is not expected any data in the response body
                .block();

        if(result != null && !result.HasErrors()) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems().stream().map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order)).toList());

            this.orderRepository.save(order);
        }else throw new IllegalArgumentException("Some products are not in stock");
    }

    public List<OrderResponse> getAll(){
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber(),
                order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
    }

    private OrderItemsResponse mapToOrderItemRequest(OrderItems orderItems){
        return new OrderItemsResponse(
                orderItems.getId(),
                orderItems.getSku(),
                orderItems.getPrice(),
                orderItems.getQuantity());
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
