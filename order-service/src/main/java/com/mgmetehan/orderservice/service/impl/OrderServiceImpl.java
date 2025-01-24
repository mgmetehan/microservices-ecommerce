package com.mgmetehan.orderservice.service.impl;

import com.mgmetehan.orderservice.client.ProductServiceClient;
import com.mgmetehan.orderservice.converter.OrderConverter;
import com.mgmetehan.orderservice.dto.request.CreateOrderRequestDTO;
import com.mgmetehan.orderservice.dto.request.OrderItemDTO;
import com.mgmetehan.orderservice.dto.request.UpdateProductRequestDTO;
import com.mgmetehan.orderservice.dto.response.ResponseOrderDTO;
import com.mgmetehan.orderservice.model.Order;
import com.mgmetehan.orderservice.model.OrderItem;
import com.mgmetehan.orderservice.model.type.OrderStatus;
import com.mgmetehan.orderservice.repository.OrderRepository;
import com.mgmetehan.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderConverter orderConverter;

    @Override
    @Transactional
    public ResponseOrderDTO createOrder(CreateOrderRequestDTO request) {
        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setItems(new ArrayList<>());

        double totalAmount = 0;

        for (OrderItemDTO itemDto : request.items()) {
            var productResponse = productServiceClient.getProduct(itemDto.productId());
            var product = productResponse.getBody().getData();

            if (product.stock() < itemDto.quantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + itemDto.productId());
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(itemDto.productId())
                    .quantity(itemDto.quantity())
                    .price(product.price())
                    .build();

            order.getItems().add(orderItem);
            totalAmount += (product.price() * itemDto.quantity());
            
            var updateStockRequest = new UpdateProductRequestDTO(
                itemDto.productId(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(product.stock() - itemDto.quantity()),
                Optional.empty()
            );
            productServiceClient.updateStock(updateStockRequest);
        }

        order.setTotalAmount(totalAmount);
        order = orderRepository.save(order);

        return orderConverter.toResponseDTO(order);
    }

    @Override
    public ResponseOrderDTO getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        return orderConverter.toResponseDTO(order);
    }

    @Override
    @Transactional
    public ResponseOrderDTO updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        order.setStatus(status);
        order = orderRepository.save(order);
        return orderConverter.toResponseDTO(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalArgumentException("Cannot cancel order with status: " + order.getStatus());
        }

        for (OrderItem item : order.getItems()) {
            var productResponse = productServiceClient.getProduct(item.getProductId());
            var currentStock = productResponse.getBody().getData().stock();
            
            var updateStockRequest = new UpdateProductRequestDTO(
                item.getProductId(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(currentStock + item.getQuantity()),
                Optional.empty()
            );
            productServiceClient.updateStock(updateStockRequest);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
} 