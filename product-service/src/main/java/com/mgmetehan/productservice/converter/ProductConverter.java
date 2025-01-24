package com.mgmetehan.productservice.converter;

import com.mgmetehan.productservice.dto.response.ResponseProductDTO;
import com.mgmetehan.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ResponseProductDTO toResponseDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ResponseProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription());
    }
} 