package br.com.hanrry.cart_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateCartItemRequestDTO(
        @NotNull
        @Min(1)
        Integer quantity
) {
}
