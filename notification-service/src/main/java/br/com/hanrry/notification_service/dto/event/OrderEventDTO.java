package br.com.hanrry.notification_service.dto.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEventDTO (
        UUID id,
        String orderCode,
        String clientName,
        String clientEmail,
        BigDecimal totalValue
){
}
