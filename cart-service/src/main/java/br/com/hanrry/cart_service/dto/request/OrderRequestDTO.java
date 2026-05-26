package br.com.hanrry.cart_service.dto.request;

import java.util.List;

public record OrderRequestDTO(
        String clientName,
        String clientEmail,
        List<OrderItemRequestDTO> items,
        OrderAddressRequestDTO address
) {
}
