package com.mgmetehan.orderservice.dto.response;

import com.mgmetehan.orderservice.dto.request.OrderItemDTO;
import com.mgmetehan.orderservice.model.type.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseOrderDTO(
    Long id,
    Long customerId,
    List<OrderItemDTO> items,
    double totalAmount,
    OrderStatus status,
    LocalDateTime createdAt
) {} 