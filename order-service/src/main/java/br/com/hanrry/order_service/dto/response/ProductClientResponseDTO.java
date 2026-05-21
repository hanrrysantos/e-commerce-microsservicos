package br.com.hanrry.order_service.dto.response;

import br.com.hanrry.order_service.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductClientResponseDTO(
        UUID id,
        String name,
        BigDecimal price,
        Integer stockQuantity,
        ProductStatus status
) {
}
