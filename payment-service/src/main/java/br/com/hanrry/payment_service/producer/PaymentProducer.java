package br.com.hanrry.payment_service.producer;

import br.com.hanrry.payment_service.dto.event.PaymentEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "payment.v1.events";

    public void sendPaymentEvent(PaymentEventDTO event) {
        String routingKey = "payment." + event.status().name().toLowerCase();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, event);
        log.info("Status do Pagamento: {}, Evento enviado ao Broker",  event.status());
    }
}