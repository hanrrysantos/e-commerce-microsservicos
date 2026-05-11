package br.com.hanrry.deliveries_service.consumer;

import br.com.hanrry.deliveries_service.config.RabbitMQConfig;
import br.com.hanrry.deliveries_service.dto.event.OrderEventDTO;
import br.com.hanrry.deliveries_service.service.DeliveriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveriesConsumer {

    private final DeliveriesService deliveriesService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DELIVERIES)
    public void consumeOrderEvent(OrderEventDTO event){
        log.info(">>> Mensagem recebida na fila de Logística: {}", event.orderCode());
        deliveriesService.processOrder(event);
    }
}
