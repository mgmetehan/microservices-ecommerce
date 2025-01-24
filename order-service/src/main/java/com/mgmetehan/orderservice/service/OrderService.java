package com.mgmetehan.orderservice.service;

import com.mgmetehan.orderservice.dto.request.CreateOrderRequestDTO;
import com.mgmetehan.orderservice.dto.response.ResponseOrderDTO;
import com.mgmetehan.orderservice.model.type.OrderStatus;

public interface OrderService {
    ResponseOrderDTO createOrder(CreateOrderRequestDTO request);
    ResponseOrderDTO getOrder(Long id);
    ResponseOrderDTO updateOrderStatus(Long id, OrderStatus status);
    void cancelOrder(Long id);
} 