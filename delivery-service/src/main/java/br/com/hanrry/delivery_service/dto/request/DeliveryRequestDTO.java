package br.com.hanrry.delivery_service.dto.request;

import br.com.hanrry.delivery_service.enums.DeliveryStatus;

public record DeliveryRequestDTO(
        DeliveryStatus status
){
}
