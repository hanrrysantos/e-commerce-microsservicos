package br.com.hanrry.product_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateStockRequestDTO(
        @NotNull
        @Min(0)
        Integer stockQuantity
) {
}
