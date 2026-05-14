package br.com.hanrry.payment_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebhookMPDTO (
        Long id,
        String live_mode,
        String type,
        String date_created,
        String userId,
        String apiVersion,
        String action,
        DataDTO data
){
}
