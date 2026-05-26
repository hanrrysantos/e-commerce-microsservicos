package br.com.hanrry.cart_service.dto.request;

public record OrderAddressRequestDTO(
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {
}
