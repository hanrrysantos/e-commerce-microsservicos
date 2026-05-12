package br.com.hanrry.payment_service.dto.event;

import br.com.hanrry.payment_service.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentEventDTO(
        String orderCode,
        String paymentId,
        BigDecimal amount,
        PaymentStatus status,
        String failureReason,
        LocalDateTime processedAt
) {}