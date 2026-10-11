package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderResponse;

public interface OrderService {
    OrderResponse getOrderById(Long id);
}
