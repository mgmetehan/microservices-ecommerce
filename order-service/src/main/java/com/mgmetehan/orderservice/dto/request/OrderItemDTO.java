package com.mgmetehan.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemDTO(
    @NotNull(message = "Product ID cannot be null")
    Long productId,
    
    @Min(value = 1, message = "Quantity must be greater than 0")
    int quantity
) {} 