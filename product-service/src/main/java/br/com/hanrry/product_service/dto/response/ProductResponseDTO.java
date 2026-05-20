package br.com.hanrry.product_service.dto.response;

import br.com.hanrry.product_service.enums.Category;
import br.com.hanrry.product_service.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        Category category,
        ProductStatus productStatus,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
