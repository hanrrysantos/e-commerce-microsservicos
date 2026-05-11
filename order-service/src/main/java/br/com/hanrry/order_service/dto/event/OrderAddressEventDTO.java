package br.com.hanrry.order_service.dto.event;

public record OrderAddressEventDTO (
        String street,
        String city,
        String state,
        String zipCode,
        String country
){
}
