package com.mgmetehan.productservice.service;

import com.mgmetehan.productservice.dto.request.CreateProductRequest;
import com.mgmetehan.productservice.dto.request.UpdateProductRequest;
import com.mgmetehan.productservice.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse updateProduct(UpdateProductRequest request);
    void deleteProduct(Long id);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
} 