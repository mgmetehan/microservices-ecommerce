package com.mgmetehan.orderservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequestDTO(
    @NotNull(message = "Customer ID cannot be null")
    Long customerId,
    
    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    List<OrderItemDTO> items
) {} 