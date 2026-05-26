package br.com.hanrry.cart_service.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemResponseDTO(
        UUID id,
        UUID productId,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {
}
