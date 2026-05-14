package br.com.hanrry.payment_service.service;

import br.com.hanrry.payment_service.database.model.PaymentEntity;
import br.com.hanrry.payment_service.database.repository.IPaymentRepository;
import br.com.hanrry.payment_service.dto.event.OrderEventDTO;
import br.com.hanrry.payment_service.dto.event.PaymentEventDTO;
import br.com.hanrry.payment_service.dto.response.PaymentResponseDTO;
import br.com.hanrry.payment_service.exception.PaymentAlreadyProcessedException;
import br.com.hanrry.payment_service.exception.PaymentNotFoundException;
import br.com.hanrry.payment_service.exception.PaymentProcessingException;
import br.com.hanrry.payment_service.mapper.IPaymentMapper;
import br.com.hanrry.payment_service.producer.PaymentProducer;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final IPaymentRepository paymentRepository;
    private final IPaymentMapper paymentMapper;
    private final PaymentProducer paymentProducer;

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public void processPaymentFromOrder(OrderEventDTO orderEvent) {

        if (paymentRepository.existsByOrderId(orderEvent.id())) {
            throw new PaymentAlreadyProcessedException("Payment already exists for order: " + orderEvent.id());
        }

        PaymentEntity payment = paymentMapper.eventDTOToEntity(orderEvent);
        PaymentEntity savedPayment = paymentRepository.save(payment);

        MercadoPagoConfig.setAccessToken(accessToken);
        PaymentClient paymentClient = new PaymentClient();

        PaymentCreateRequest paymentRequest = PaymentCreateRequest.builder()
                .transactionAmount(orderEvent.totalValue())
                .paymentMethodId("pix")
                .payer(
                        PaymentPayerRequest.builder()
                                .email("hanrry.jsantos@gmail.com")
                                .build()
                )
                .build();

        try {
            Payment mpPayment = paymentClient.create(paymentRequest);

            savedPayment.setExternalId(mpPayment.getId().toString());
            savedPayment.setQrCode(
                    mpPayment.getPointOfInteraction()
                            .getTransactionData()
                            .getQrCode()
            );

            PaymentEntity updatedPayment = paymentRepository.save(savedPayment);

            log.info("Pagamento criado com sucesso para o pedido: {}", orderEvent.id());

        } catch (MPException | MPApiException e) {
            log.error("Erro ao processar pagamento para o pedido {}: {}", orderEvent.id(), e.getMessage(), e);
            throw new PaymentProcessingException("Error processing payment for order: " + orderEvent.id(), e);
        }
    }

    public PaymentResponseDTO findPaymentById(UUID id) {
        PaymentEntity payment = paymentRepository.findById(id).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found with this id: " + id)
        );
        return paymentMapper.toDTO(payment);
    }

    public PaymentResponseDTO findPaymentByOrderId(UUID orderId) {
        PaymentEntity payment = paymentRepository.findByOrderId(orderId).orElseThrow(
                () -> new PaymentNotFoundException("Payment not found with this order id: " + orderId)
        );
        return paymentMapper.toDTO(payment);
    }

    public List<PaymentResponseDTO> findAllPayments() {
        List<PaymentEntity> payments = paymentRepository.findAll();
        return paymentMapper.toDTOList(payments);
    }
}