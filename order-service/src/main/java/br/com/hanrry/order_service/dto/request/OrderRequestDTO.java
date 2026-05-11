package br.com.hanrry.order_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO (

    @NotBlank
    String clientName,

    @NotBlank
    @Email
    String clientEmail,

    @NotEmpty
    List<OrderItemRequestDTO> items,

    @NotNull
    OrderAddressRequestDTO address

){
}
