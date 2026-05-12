package br.com.hanrry.notification_service.dto.response;

import br.com.hanrry.notification_service.enums.NotificationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationResponseDTO (
        UUID id,
        UUID orderId,
        String name,
        String clientEmail,
        String subject,
        String content,
        NotificationStatus status,
        LocalDateTime createdAt,
        LocalDateTime sentAt
){
}
