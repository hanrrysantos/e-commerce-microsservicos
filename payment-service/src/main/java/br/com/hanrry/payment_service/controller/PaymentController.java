package br.com.hanrry.payment_service.controller;

import br.com.hanrry.payment_service.dto.request.PaymentRequestDTO;
import br.com.hanrry.payment_service.dto.response.PaymentResponseDTO;
import br.com.hanrry.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> processPayment(
            @RequestBody @Valid PaymentRequestDTO requestDTO) {

        PaymentResponseDTO response = paymentService.processPayment(requestDTO);
        return ResponseEntity.ok(response);
    }
}