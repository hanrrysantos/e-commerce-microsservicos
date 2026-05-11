package br.com.hanrry.deliveries_service.dto;

public record OrderAddressEventDTO (
        String street,
        String city,
        String state,
        String zipCode,
        String country
){
}
