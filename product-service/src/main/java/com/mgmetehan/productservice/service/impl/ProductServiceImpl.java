package com.mgmetehan.productservice.service.impl;

import com.mgmetehan.productservice.dto.request.CreateProductRequest;
import com.mgmetehan.productservice.dto.request.UpdateProductRequest;
import com.mgmetehan.productservice.dto.response.ProductResponse;
import com.mgmetehan.productservice.model.Product;
import com.mgmetehan.productservice.repository.ProductRepository;
import com.mgmetehan.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .description(request.description())
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(UpdateProductRequest request) {
        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + request.id()));

        request.name().ifPresent(product::setName);
        request.price().ifPresent(product::setPrice);
        request.stock().ifPresent(product::setStock);
        request.description().ifPresent(product::setDescription);

        Product updatedProduct = productRepository.save(product);
        return convertToProductResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return convertToProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    private ProductResponse convertToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription()
        );
    }
} 