package com.mgmetehan.orderservice.controller;

import com.mgmetehan.orderservice.dto.ApiResponse;
import com.mgmetehan.orderservice.dto.request.CreateOrderRequestDTO;
import com.mgmetehan.orderservice.dto.response.ResponseOrderDTO;
import com.mgmetehan.orderservice.model.type.OrderStatus;
import com.mgmetehan.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<ResponseOrderDTO>> createOrder(@Valid @RequestBody CreateOrderRequestDTO request) {
        try {
            ResponseOrderDTO response = orderService.createOrder(request);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Order created successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponseOrderDTO>> getOrder(@PathVariable Long id) {
        try {
            ResponseOrderDTO response = orderService.getOrder(id);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Order retrieved successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ResponseOrderDTO>> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        try {
            ResponseOrderDTO response = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Order status updated successfully", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long id) {
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Order cancelled successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>("ERROR", e.getMessage(), null));
        }
    }
} 