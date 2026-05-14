package br.com.hanrry.payment_service.controller;

import br.com.hanrry.payment_service.dto.response.PaymentResponseDTO;
import br.com.hanrry.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable UUID id) {
        PaymentResponseDTO response = paymentService.findPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDTO> findByOrderId(@PathVariable UUID orderId) {
        PaymentResponseDTO response = paymentService.findPaymentByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> findAll() {
        List<PaymentResponseDTO> response = paymentService.findAllPayments();
        return ResponseEntity.ok(response);
    }
}