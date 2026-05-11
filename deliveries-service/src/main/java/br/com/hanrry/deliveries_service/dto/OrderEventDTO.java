package br.com.hanrry.deliveries_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEventDTO(
        UUID id,
        String orderCode,
        String clientName,
        String clientEmail,
        BigDecimal totalValue
){
}
