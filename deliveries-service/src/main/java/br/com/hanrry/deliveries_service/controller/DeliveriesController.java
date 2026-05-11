package br.com.hanrry.deliveries_service.controller;

import br.com.hanrry.deliveries_service.dto.request.DeliveriesRequestDTO;
import br.com.hanrry.deliveries_service.dto.response.DeliveriesResponseDTO;
import br.com.hanrry.deliveries_service.service.DeliveriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/deliveries")
@RequiredArgsConstructor
public class DeliveriesController {

    private final DeliveriesService deliveriesService;

    @PatchMapping("/{id}/status")
    public ResponseEntity<DeliveriesResponseDTO> updateStatus(
            @PathVariable UUID id,
            @RequestBody DeliveriesRequestDTO request) {
        return ResponseEntity.ok(deliveriesService.updateDeliveryStatus(id, request.status()));
    }

}
