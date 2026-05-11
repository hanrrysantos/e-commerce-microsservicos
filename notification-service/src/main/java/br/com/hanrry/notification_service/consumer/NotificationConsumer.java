package br.com.hanrry.notification_service.consumer;

import br.com.hanrry.notification_service.dto.OrderEventDTO;
import br.com.hanrry.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "queue.notifications")
    public void listenEmailQueue(@Payload OrderEventDTO eventDTO){

        notificationService.processNotification(eventDTO);

    }


}
