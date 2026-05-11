package br.com.hanrry.deliveries_service.dto.response;

import br.com.hanrry.deliveries_service.enums.DeliveriesStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveriesResponseDTO (
        UUID id,
        UUID orderId,
        String clientName,
        String clientEmail,
        String street,
        String city,
        String state,
        String zipCode,
        String country,
        DeliveriesStatus status,
        String trackingCode,
        LocalDateTime deliveredAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
