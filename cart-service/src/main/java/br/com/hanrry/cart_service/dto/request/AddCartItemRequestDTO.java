package br.com.hanrry.cart_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record AddCartItemRequestDTO(
        @NotNull
        UUID productId,

        @NotNull
        @Min(1)
        Integer quantity
) {
}
