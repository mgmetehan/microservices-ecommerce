package com.mgmetehan.orderservice.converter;

import com.mgmetehan.orderservice.dto.request.OrderItemDTO;
import com.mgmetehan.orderservice.dto.response.ResponseOrderDTO;
import com.mgmetehan.orderservice.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    public ResponseOrderDTO toResponseDTO(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItemDTO> itemDtos = order.getItems().stream()
                .map(item -> new OrderItemDTO(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new ResponseOrderDTO(
                order.getId(),
                order.getCustomerId(),
                itemDtos,
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
} 