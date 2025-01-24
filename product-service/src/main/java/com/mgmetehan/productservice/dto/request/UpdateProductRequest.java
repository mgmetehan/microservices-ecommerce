package com.mgmetehan.productservice.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateProductRequest(
        @NotNull(message = "Id cannot be null")
        Long id,
        Optional<String> name,
        Optional<BigDecimal> price,
        Optional<Integer> stock,
        Optional<String> description
) {
} 