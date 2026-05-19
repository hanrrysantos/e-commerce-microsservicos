package br.com.hanrry.delivery_service.controller;

import br.com.hanrry.delivery_service.dto.request.DeliveryRequestDTO;
import br.com.hanrry.delivery_service.dto.response.DeliveryResponseDTO;
import br.com.hanrry.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveryResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody DeliveryRequestDTO request) {
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(id, request.status()));
    }

}
