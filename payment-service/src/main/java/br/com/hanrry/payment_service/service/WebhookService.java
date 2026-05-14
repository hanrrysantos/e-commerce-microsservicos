package br.com.hanrry.payment_service.service;

import br.com.hanrry.payment_service.database.model.PaymentEntity;
import br.com.hanrry.payment_service.database.repository.IPaymentRepository;
import br.com.hanrry.payment_service.dto.event.PaymentEventDTO;
import br.com.hanrry.payment_service.dto.request.WebhookMPDTO;
import br.com.hanrry.payment_service.enums.PaymentStatus;
import br.com.hanrry.payment_service.exception.PaymentNotFoundException;
import br.com.hanrry.payment_service.mapper.IPaymentMapper;
import br.com.hanrry.payment_service.producer.PaymentProducer;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private static final Logger log = LoggerFactory.getLogger(WebhookService.class);

    private final IPaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;
    private final IPaymentMapper paymentMapper;

    @Value("${mercadopago.access-token}")
    private String accessToken;

    public void processWebhook(WebhookMPDTO payload) {
        if (!"payment".equals(payload.type())) {
            log.info("Webhook ignorado, tipo não suportado: {}", payload.type());
            return;
        }

        Long mpPaymentId = Long.parseLong(payload.data().id());

        try {
            MercadoPagoConfig.setAccessToken(accessToken);
            PaymentClient client = new PaymentClient();

            Payment mpPayment = client.get(mpPaymentId);

            PaymentEntity paymentEntity = paymentRepository.findByExternalId(mpPayment.getId().toString())
                    .orElseThrow(() -> new PaymentNotFoundException("Pagamento não encontrado"));

            PaymentStatus status = switch (mpPayment.getStatus()) {
                case "approved"       -> PaymentStatus.APPROVED;
                case "authorized"     -> PaymentStatus.AUTHORIZED;
                case "in_process"     -> PaymentStatus.IN_PROCESS;
                case "in_mediation"   -> PaymentStatus.IN_MEDIATION;
                case "rejected"       -> PaymentStatus.REJECTED;
                case "cancelled"      -> PaymentStatus.CANCELLED;
                case "refunded"       -> PaymentStatus.REFUNDED;
                case "charged_back"   -> PaymentStatus.CHARGED_BACK;
                default               -> PaymentStatus.PENDING;
            };

            paymentEntity.setStatus(status);

            if (status == PaymentStatus.PENDING ||
                    status == PaymentStatus.IN_PROCESS ||
                    status == PaymentStatus.IN_MEDIATION ||
                    status == PaymentStatus.AUTHORIZED) {
                log.info("Pagamento {} ainda em processamento: {}", mpPaymentId, status);
                return;
            }

            paymentRepository.save(paymentEntity);

            PaymentEventDTO event = paymentMapper.toEventDTO(paymentEntity);
            paymentProducer.sendPaymentEvent(event);

            log.info("Pagamento {} atualizado para: {}", mpPaymentId, paymentEntity.getStatus());

        } catch (Exception e) {
            log.error("Erro ao processar webhook para pagamento {}: {}", mpPaymentId, e.getMessage(), e);
            throw new RuntimeException("Erro ao processar webhook do Mercado Pago", e);
        }
    }

    public void processTestApproval(String externalId) {
        PaymentEntity payment = paymentRepository.findByExternalId(externalId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.APPROVED);
        paymentRepository.save(payment);

        PaymentEventDTO event = paymentMapper.toEventDTO(payment);
        paymentProducer.sendPaymentEvent(event);

        log.info("Pagamento {} aprovado manualmente para teste", externalId);
    }
}