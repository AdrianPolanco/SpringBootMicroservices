package com.adriandev.orders.model.dtos;

import com.adriandev.orders.model.entities.OrderItems;

import java.util.List;

public record OrderResponse(Long id, String orderNumber, List<OrderItemsResponse> orderItems) {
}
