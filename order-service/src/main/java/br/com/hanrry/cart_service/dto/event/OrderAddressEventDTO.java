package br.com.hanrry.cart_service.dto.event;

public record OrderAddressEventDTO (
        String street,
        String city,
        String state,
        String zipCode,
        String country
){
}
