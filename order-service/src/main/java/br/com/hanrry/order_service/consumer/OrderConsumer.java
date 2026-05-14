package br.com.hanrry.order_service.consumer;

import br.com.hanrry.order_service.config.RabbitMQConfig;
import br.com.hanrry.order_service.dto.event.PaymentEventDTO;
import br.com.hanrry.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderConsumer {

    private final OrderService orderService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PAYMENT_EVENTS)
    public void consumePaymentEvent(PaymentEventDTO event) {
        log.info("Evento de pagamento recebido: {}", event.status());
        orderService.updateOrderStatusFromPayment(event);
    }

}
