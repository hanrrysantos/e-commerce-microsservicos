package br.com.hanrry.order_service.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequestDTO(
    UUID productId,
    String productName,
    Integer quantity,
    BigDecimal unitPrice
) {
}
