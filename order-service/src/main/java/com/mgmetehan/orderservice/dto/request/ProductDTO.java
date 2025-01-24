package com.mgmetehan.orderservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductDTO(
        Long id,

        @NotBlank(message = "Name cannot be null or blank")
        String name,

        @Min(value = 0, message = "Price must be greater than 0")
        double price,

        @Min(value = 0, message = "Stock cannot be negative")
        int stock,

        String description
) {
}