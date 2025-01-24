package com.mgmetehan.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        BigDecimal price,
        @NotNull(message = "Stock cannot be null")
        @Positive(message = "Stock must be positive")
        Integer stock,
        @NotBlank(message = "Description cannot be blank")
        String description
) {
} 