package com.ecommerce.order.service;

import com.ecommerce.order.entity.Order;
import com.ecommerce.order.exception.ResourceNotFoundException;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("Should throw ResourceNotFoundException when order does not exist")
    void testGetOrderByIdNotFound() {
        Long orderId = 999L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.getOrderById(orderId)
        );

        assertEquals("Order với ID 999 không tồn tại trên hệ thống!", exception.getMessage());
    }

    @Test
    @DisplayName("Should return OrderResponse when order exists")
    void testGetOrderByIdSuccess() {
        Long orderId = 1L;
        Order order = Order.builder()
                .id(orderId)
                .customerName("Nguyễn Văn A")
                .totalAmount(BigDecimal.valueOf(1500000))
                .status("COMPLETED")
                .createdAt(LocalDateTime.now())
                .build();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        var response = orderService.getOrderById(orderId);

        assertNotNull(response);
        assertEquals(orderId, response.getId());
        assertEquals("Nguyễn Văn A", response.getCustomerName());
        assertEquals(BigDecimal.valueOf(1500000), response.getTotalAmount());
        assertEquals("COMPLETED", response.getStatus());
    }
}
