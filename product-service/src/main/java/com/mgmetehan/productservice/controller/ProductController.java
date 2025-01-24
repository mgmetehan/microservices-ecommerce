package com.mgmetehan.productservice.controller;

import com.mgmetehan.productservice.dto.ApiResponse;
import com.mgmetehan.productservice.dto.request.CreateProductRequestDTO;
import com.mgmetehan.productservice.dto.request.UpdateProductRequestDTO;
import com.mgmetehan.productservice.dto.response.ResponseProductDTO;
import com.mgmetehan.productservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResponseProductDTO>> createProduct(@Valid @RequestBody CreateProductRequestDTO request) {
        try {
            ResponseProductDTO response = productService.createProduct(request);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResponseProductDTO>> updateProduct(@Valid @RequestBody UpdateProductRequestDTO request) {
        try {
            ResponseProductDTO response = productService.updateProduct(request);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseProductDTO>> getProductById(@PathVariable Long id) {
        try {
            ResponseProductDTO response = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Product retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseProductDTO>>> getAllProducts() {
        try {
            List<ResponseProductDTO> response = productService.getAllProducts();
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Products retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }
} 