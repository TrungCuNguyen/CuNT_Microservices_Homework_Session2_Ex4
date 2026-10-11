package com.ecommerce.order.controller;

import com.ecommerce.order.exception.GlobalExceptionHandler;
import com.ecommerce.order.exception.ResourceNotFoundException;
import com.ecommerce.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(GlobalExceptionHandler.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    @DisplayName("GET /api/v1/orders/999 - Should return 404 with ApiResponseError format when order does not exist")
    void testGetOrderByIdNotFound() throws Exception {
        Long nonExistentId = 999L;
        when(orderService.getOrderById(nonExistentId))
                .thenThrow(new ResourceNotFoundException("Order với ID " + nonExistentId + " không tồn tại trên hệ thống!"));

        mockMvc.perform(get("/api/v1/orders/{id}", nonExistentId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Order với ID 999 không tồn tại trên hệ thống!"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }
}
