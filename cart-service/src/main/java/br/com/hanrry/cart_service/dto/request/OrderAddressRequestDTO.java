package br.com.hanrry.cart_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OrderAddressRequestDTO(
        @NotBlank
        String street,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotBlank
        String zipCode,

        @NotBlank
        String country
) {
}
