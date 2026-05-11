package br.com.hanrry.order_service.dto.event;

import br.com.hanrry.order_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderEventDTO (
        UUID id,
        String orderCode,
        String clientName,
        String clientEmail,
        OrderStatus status,
        BigDecimal totalValue,
        List<OrderItemEventDTO> items,
        OrderAddressEventDTO address
){
}
