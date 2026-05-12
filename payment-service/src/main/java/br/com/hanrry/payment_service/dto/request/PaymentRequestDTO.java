package br.com.hanrry.payment_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequestDTO(
        @NotBlank(message = "Order code is required")
        String orderCode,
        @NotNull(message = "Amount is required")
        BigDecimal amount,
        @NotBlank(message = "Payment method is required")
        String paymentMethod,
        String cardNumber,
        String cardHolderName,
        String expiryDate,
        String cvv
) {}