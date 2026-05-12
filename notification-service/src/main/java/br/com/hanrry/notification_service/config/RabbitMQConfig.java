package br.com.hanrry.notification_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_ORDER_NOTIFICATIONS = "queue.order.notifications";
    public static final String QUEUE_USER_NOTIFICATIONS = "queue.user.notifications";

    public static final String EXCHANGE_ORDER_NAME = "order.v1.events";
    public static final String EXCHANGE_USER_NAME = "user.v1.events";

    public static final String ORDER_BINDING_KEY = "order.#";
    public static final String USER_BINDING_KEY = "user.#";

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_ORDER_NOTIFICATIONS, true);
    }

    @Bean
    public Queue userNotificationQueue() {
        return new Queue(QUEUE_USER_NOTIFICATIONS, true);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE_ORDER_NAME);
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(EXCHANGE_USER_NAME);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange orderExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(orderExchange)
                .with(ORDER_BINDING_KEY);
    }

    @Bean
    public Binding userNotificationBinding(Queue userNotificationQueue, TopicExchange userExchange) {
        return BindingBuilder
                .bind(userNotificationQueue)
                .to(userExchange)
                .with(USER_BINDING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
