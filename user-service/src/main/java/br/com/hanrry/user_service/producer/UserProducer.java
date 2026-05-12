package br.com.hanrry.user_service.producer;

import br.com.hanrry.user_service.dto.event.UserEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE_NAME = "user.v1.events";

    public void publishMessageEmail(UserEventDTO event){
       String routingKey = String.format("user.%s", event.status().name().toLowerCase());

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, event);
        log.info(">>> [LOG] Evento enviado para CloudAMQP | Key: " + routingKey);
    }


}
