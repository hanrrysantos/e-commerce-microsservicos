package br.com.hanrry.product_service.dto.request;

import br.com.hanrry.product_service.enums.Category;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDTO (

        @NotBlank
        String name,
        String description,

        @NotNull
        @DecimalMin("0.01")
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stockQuantity,

        @NotNull
        Category category
){
}
