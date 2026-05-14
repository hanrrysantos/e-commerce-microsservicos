package br.com.hanrry.notification_service.dto.event;

import br.com.hanrry.notification_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEventDTO (
        UUID id,
        String orderCode,
        String clientName,
        String clientEmail,
        OrderStatus status,
        BigDecimal totalValue
){
}
