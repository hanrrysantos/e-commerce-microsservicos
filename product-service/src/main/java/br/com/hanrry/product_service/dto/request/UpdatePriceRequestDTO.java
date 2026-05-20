package br.com.hanrry.product_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdatePriceRequestDTO (
        @NotNull
        @DecimalMin("0.01")
        BigDecimal price
){
}
