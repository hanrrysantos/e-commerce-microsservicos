package br.com.hanrry.delivery_service.consumer;

import br.com.hanrry.delivery_service.config.RabbitMQConfig;
import br.com.hanrry.delivery_service.dto.event.OrderEventDTO;
import br.com.hanrry.delivery_service.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryConsumer {

    private final DeliveryService deliveryService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELIVERIES)
    public void consumeOrderEvent(OrderEventDTO event){
        log.info("Evento recebido na fila de Entrega: {}", event.orderCode());
        deliveryService.processOrder(event);
    }
}
