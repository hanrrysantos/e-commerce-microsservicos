package br.com.hanrry.payment_service.consumer;

import br.com.hanrry.payment_service.config.RabbitMQConfig;
import br.com.hanrry.payment_service.dto.event.OrderEventDTO;
import br.com.hanrry.payment_service.exception.PaymentAlreadyProcessedException;
import br.com.hanrry.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PAYMENTS)
    public void consumeOrderEvent(OrderEventDTO event) {
        log.info("Pedido recebido: {}, Amount: {}, Aguardando Pagamento...",
                event.orderCode(), event.totalValue());
        try {
            paymentService.processPaymentFromOrder(event);
        } catch (PaymentAlreadyProcessedException e) {
            log.warn("Pagamento já processado para o pedido: {}. Ignorando mensagem.", event.id());
        }
    }
}