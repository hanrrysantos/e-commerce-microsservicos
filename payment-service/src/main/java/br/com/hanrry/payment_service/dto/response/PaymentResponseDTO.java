package br.com.hanrry.payment_service.dto.response;

import br.com.hanrry.payment_service.enums.PaymentStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        UUID orderId,
        PaymentStatus status,
        String qrCode,
        LocalDateTime createdAt,
        String externalId
) {}