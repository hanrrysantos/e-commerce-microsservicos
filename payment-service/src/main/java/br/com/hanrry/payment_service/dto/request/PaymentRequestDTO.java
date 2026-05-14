package br.com.hanrry.payment_service.dto.request;

import br.com.hanrry.payment_service.enums.PaymentMethod;
import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequestDTO(
        UUID orderId,
        String clientEmail,
        BigDecimal amount,
        PaymentMethod method
) {}