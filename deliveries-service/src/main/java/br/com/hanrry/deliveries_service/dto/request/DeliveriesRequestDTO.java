package br.com.hanrry.deliveries_service.dto.request;

import br.com.hanrry.deliveries_service.enums.DeliveriesStatus;

public record DeliveriesRequestDTO(
        DeliveriesStatus status
){
}
