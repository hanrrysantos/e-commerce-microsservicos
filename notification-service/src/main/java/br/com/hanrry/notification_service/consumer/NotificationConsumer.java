package br.com.hanrry.notification_service.consumer;

import br.com.hanrry.notification_service.config.RabbitMQConfig;
import br.com.hanrry.notification_service.dto.event.OrderEventDTO;
import br.com.hanrry.notification_service.dto.event.PaymentEventDTO;
import br.com.hanrry.notification_service.dto.event.UserEventDTO;
import br.com.hanrry.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER_NOTIFICATIONS)
    public void listenEmailQueue(@Payload OrderEventDTO eventDTO){
        log.info("Evento recebido na fila queue.order.notifications");
        notificationService.processOrderNotification(eventDTO);

    }


    @RabbitListener(queues = RabbitMQConfig.QUEUE_USER_NOTIFICATIONS)
    public void listenEmailQueue(@Payload UserEventDTO eventDTO){
        log.info("Evento recebido na fila queue.user.notifications");
        notificationService.processUserNotification(eventDTO);

    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_PAYMENT_NOTIFICATIONS)
    public void listenPaymentQueue(@Payload PaymentEventDTO eventDTO) {
        log.info("Evento recebido na fila queue.payment.notifications");
        notificationService.processPaymentNotification(eventDTO);
    }
}
