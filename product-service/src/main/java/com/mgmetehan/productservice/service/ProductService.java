package com.mgmetehan.productservice.service;

import com.mgmetehan.productservice.dto.request.CreateProductRequestDTO;
import com.mgmetehan.productservice.dto.request.UpdateProductRequestDTO;
import com.mgmetehan.productservice.dto.response.ResponseProductDTO;

import java.util.List;

public interface ProductService {
    ResponseProductDTO createProduct(CreateProductRequestDTO request);
    ResponseProductDTO updateProduct(UpdateProductRequestDTO request);
    void deleteProduct(Long id);
    ResponseProductDTO getProductById(Long id);
    List<ResponseProductDTO> getAllProducts();
} 