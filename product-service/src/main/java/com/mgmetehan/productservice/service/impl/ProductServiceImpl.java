package com.mgmetehan.productservice.service.impl;

import com.mgmetehan.productservice.dto.request.CreateProductRequestDTO;
import com.mgmetehan.productservice.dto.request.UpdateProductRequestDTO;
import com.mgmetehan.productservice.dto.response.ResponseProductDTO;
import com.mgmetehan.productservice.model.Product;
import com.mgmetehan.productservice.repository.ProductRepository;
import com.mgmetehan.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponseProductDTO createProduct(CreateProductRequestDTO request) {
        log.info("Creating product: {}", request);
        Product product = Product.builder()
                .name(request.name())
                .price(request.price())
                .stock(request.stock())
                .description(request.description())
                .build();

        Product savedProduct = productRepository.save(product);
        return convertToResponseProductDTO(savedProduct);
    }

    @Override
    public ResponseProductDTO updateProduct(UpdateProductRequestDTO request) {
        Product product = productRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + request.id()));

        request.name().ifPresent(product::setName);
        request.price().ifPresent(product::setPrice);
        request.stock().ifPresent(product::setStock);
        request.description().ifPresent(product::setDescription);

        Product updatedProduct = productRepository.save(product);
        return convertToResponseProductDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public ResponseProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
        return convertToResponseProductDTO(product);
    }

    @Override
    public List<ResponseProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToResponseProductDTO)
                .toList();
    }

    private ResponseProductDTO convertToResponseProductDTO(Product product) {
        return new ResponseProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription()
        );
    }
} 