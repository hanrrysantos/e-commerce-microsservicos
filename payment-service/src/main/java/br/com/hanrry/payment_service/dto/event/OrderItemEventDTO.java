package br.com.hanrry.payment_service.dto.event;

import java.math.BigDecimal;

public record OrderItemEventDTO(
        String productCode,
        String productName,
        BigDecimal unitPrice,
        Integer quantity
) {}