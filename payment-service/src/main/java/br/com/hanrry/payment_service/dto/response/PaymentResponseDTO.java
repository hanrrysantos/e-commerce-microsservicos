package br.com.hanrry.payment_service.dto.response;

import br.com.hanrry.payment_service.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDTO(
        String paymentId,
        String orderCode,
        BigDecimal amount,
        PaymentStatus status,
        String message,
        LocalDateTime processedAt
) {}