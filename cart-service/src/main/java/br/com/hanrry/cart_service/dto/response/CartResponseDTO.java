package br.com.hanrry.cart_service.dto.response;

import br.com.hanrry.cart_service.enums.CartStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CartResponseDTO(
        UUID id,
        String clientEmail,
        CartStatus status,
        BigDecimal totalValue,
        List<CartItemResponseDTO> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
