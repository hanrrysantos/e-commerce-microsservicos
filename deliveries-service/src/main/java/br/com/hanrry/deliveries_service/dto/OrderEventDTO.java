package br.com.hanrry.deliveries_service.dto;

import br.com.hanrry.deliveries_service.enums.CategoryProduct;
import br.com.hanrry.deliveries_service.enums.TypeProduct;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEventDTO(
        UUID id,
        String orderCode,
        String clientName,
        String clientEmail,
        BigDecimal totalValue,
        TypeProduct typeProduct,
        CategoryProduct categoryProduct
){
}
