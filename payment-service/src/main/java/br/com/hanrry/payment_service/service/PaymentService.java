package br.com.hanrry.payment_service.service;

import br.com.hanrry.payment_service.dto.event.OrderEventDTO;
import br.com.hanrry.payment_service.dto.event.PaymentEventDTO;
import br.com.hanrry.payment_service.dto.request.PaymentRequestDTO;
import br.com.hanrry.payment_service.dto.response.PaymentResponseDTO;
import br.com.hanrry.payment_service.enums.PaymentStatus;
import br.com.hanrry.payment_service.producer.PaymentProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentProducer paymentProducer;

    @Autowired
    public PaymentService(PaymentProducer paymentProducer) {
        this.paymentProducer = paymentProducer;
    }

    public void processPaymentFromOrder(OrderEventDTO orderEvent) {
        PaymentRequestDTO paymentRequest = new PaymentRequestDTO(
                orderEvent.orderCode(),
                orderEvent.totalAmount(),
                "PIX",
                null,
                null,
                null,
                null
        );

        PaymentResponseDTO response = processPayment(paymentRequest);

        PaymentEventDTO paymentEvent = new PaymentEventDTO(
                orderEvent.orderCode(),
                response.paymentId(),
                response.amount(),
                response.status(),
                response.status() == PaymentStatus.FAILED ? response.message() : null,
                response.processedAt()
        );

        paymentProducer.sendPaymentEvent(paymentEvent);
    }

    public PaymentResponseDTO processPayment(PaymentRequestDTO request) {
        String paymentId = UUID.randomUUID().toString();

        PaymentStatus status = simulatePaymentProcessing(request);

        String message = switch (status) {
            case CONFIRMED -> "Payment confirmed successfully";
            case FAILED -> "Payment failed - insufficient funds";
            default -> "Payment pending";
        };

        return new PaymentResponseDTO(
                paymentId,
                request.orderCode(),
                request.amount(),
                status,
                message,
                LocalDateTime.now()
        );
    }

    private PaymentStatus simulatePaymentProcessing(PaymentRequestDTO request) {
        if (request.amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return PaymentStatus.FAILED;
        }

        if (request.orderCode().contains("FAIL")) {
            return PaymentStatus.FAILED;
        }

        return PaymentStatus.CONFIRMED;
    }
}