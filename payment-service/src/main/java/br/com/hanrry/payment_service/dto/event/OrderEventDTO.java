package br.com.hanrry.payment_service.dto.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderEventDTO(
        String orderCode,
        String customerName,
        String customerEmail,
        BigDecimal totalAmount,
        String status,
        List<OrderItemEventDTO> items,
        OrderAddressEventDTO address,
        LocalDateTime createdAt
) {}