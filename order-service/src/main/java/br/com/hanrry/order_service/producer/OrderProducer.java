package br.com.hanrry.order_service.producer;

import br.com.hanrry.order_service.dto.event.OrderEventDTO;
import br.com.hanrry.order_service.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "order.v1.events";

    public void sendOrderEvent(OrderEventDTO event) {
        String routingKey = String.format("order.%s",
            event.status().name().toLowerCase());
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, event);

        log.info(">>> [LOG] Evento enviado para CloudAMQP | Key: " + routingKey);
    }
}
