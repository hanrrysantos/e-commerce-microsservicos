package br.com.hanrry.deliveries_service.dto.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemEventDTO (
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subTotal
){
}
