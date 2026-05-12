package br.com.hanrry.payment_service.producer;

import br.com.hanrry.payment_service.dto.event.PaymentEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "order.v1.events";

    public void sendPaymentEvent(PaymentEventDTO event) {
        String routingKey = "order." + event.status().name().toLowerCase();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, event);
        System.out.println(">>> [LOG] Payment event sent | Order: " + event.orderCode() + " | Status: " + event.status());
    }
}