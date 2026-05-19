package br.com.hanrry.delivery_service.dto.response;

import br.com.hanrry.delivery_service.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryResponseDTO(
        UUID id,
        UUID orderId,
        String clientName,
        String clientEmail,
        String street,
        String city,
        String state,
        String zipCode,
        String country,
        DeliveryStatus status,
        String trackingCode,
        LocalDateTime deliveredAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
}
