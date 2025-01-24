package com.mgmetehan.productservice.dto.response;

import java.math.BigDecimal;

public record ResponseProductDTO(
        Long id,
        String name,
        BigDecimal price,
        Integer stock,
        String description
) {
} 