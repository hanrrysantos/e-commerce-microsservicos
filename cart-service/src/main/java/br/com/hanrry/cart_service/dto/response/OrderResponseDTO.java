package br.com.hanrry.cart_service.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        String orderCode,
        String clientName,
        String clientEmail,
        String status,
        BigDecimal totalValue
) {
}
