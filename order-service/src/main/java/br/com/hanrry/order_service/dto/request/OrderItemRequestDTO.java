package br.com.hanrry.order_service.dto.request;

import java.util.UUID;

public record OrderItemRequestDTO(
    UUID productId,
    Integer quantity
) {
}
