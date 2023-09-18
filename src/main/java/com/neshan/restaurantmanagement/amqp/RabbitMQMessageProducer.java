package com.neshan.restaurantmanagement.amqp;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMQMessageProducer {

    private final AmqpTemplate amqpTemplate;

    public void publish(String exchange, String routingKey, byte[] payload) {
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
