package br.com.hanrry.cart_service.dto.response;

public record OrderAddressResponseDTO(
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {
}
