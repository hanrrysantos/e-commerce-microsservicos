package br.com.hanrry.cart_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckoutCartRequestDTO(
        @NotBlank
        String clientName,

        @NotNull
        @Valid
        OrderAddressRequestDTO address
) {
}
