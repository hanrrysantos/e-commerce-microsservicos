package br.com.hanrry.payment_service.controller;

import br.com.hanrry.payment_service.dto.request.WebhookMPDTO;
import br.com.hanrry.payment_service.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/webhooks")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping("/mercadopago")
    public ResponseEntity<Void> receiveWebhook(@RequestBody WebhookMPDTO payload) {
        webhookService.processWebhook(payload);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test/approve/{externalId}")
    public ResponseEntity<Void> testApprove(@PathVariable String externalId) {
       webhookService.processTestApproval(externalId);
        return ResponseEntity.ok().build();
    }
}