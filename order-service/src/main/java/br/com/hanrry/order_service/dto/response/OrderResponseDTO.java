package br.com.hanrry.order_service.dto.response;

import br.com.hanrry.order_service.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO (

    UUID id,
    String orderCode,
    String clientName,
    String clientEmail,
    OrderStatus status,
    BigDecimal totalValue,
    List<OrderItemResponseDTO> items,
    OrderAddressResponseDTO address,
    LocalDateTime createdAt

){
}
