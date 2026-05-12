package br.com.hanrry.payment_service.dto.event;

public record OrderAddressEventDTO(
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {}