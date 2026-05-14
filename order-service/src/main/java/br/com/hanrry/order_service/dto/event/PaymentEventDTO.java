package br.com.hanrry.order_service.dto.event;

import br.com.hanrry.order_service.enums.PaymentMethod;
import br.com.hanrry.order_service.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentEventDTO(
        UUID paymentId,
        UUID orderId,
        String clientEmail,
        String clientName,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        LocalDateTime processedAt
) {
}
